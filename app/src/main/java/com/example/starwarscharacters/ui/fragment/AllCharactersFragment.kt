package com.example.starwarscharacters.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.R
import com.example.starwarscharacters.common.Constants.QUERY_PAGE_SIZE
import com.example.starwarscharacters.common.Constants.SEARCH_TIME_DELAY
import com.example.starwarscharacters.common.Resource
import com.example.starwarscharacters.databinding.FragmentAllCharactersBinding
import com.example.starwarscharacters.network.NetworkConnectivityObserver
import com.example.starwarscharacters.ui.activity.MainActivity
import com.example.starwarscharacters.ui.activity.MainActivityViewModel
import com.example.starwarscharacters.ui.adapters.AllCharactersAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AllCharactersFragment : Fragment(R.layout.fragment_all_characters) {

    private lateinit var binding: FragmentAllCharactersBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var allCharactersAdapter: AllCharactersAdapter
    private var networkManager = NetworkConnectivityObserver()
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = true



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        if (viewModel.characters.value == null) {
            makeCharactersRequest()
        }

        setContext()
        setupAllCharactersRecyclerView()

    }






    private fun setupAllCharactersRecyclerView() {
        allCharactersAdapter = AllCharactersAdapter()
        binding.charactersRecyclerview.apply {
            adapter = allCharactersAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@AllCharactersFragment.scrollListener)
        }
        allCharactersAdapter.setOnItemClickListener {
            val action: NavDirections = AllCharactersFragmentDirections.actionAllCharactersFragmentToCharacterFragment(it)
            findNavController().navigate(
                action
            )
        }
        var job: Job? = null
        binding.searchView.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchCharacters(editable.toString())
                    }
                }
            }
        }

    }


    private fun hideProgressBar() {
        binding.charactersProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.charactersProgressBar.visibility = View.VISIBLE
        isLoading = true
    }


    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage
                        && isAtLastItem && isNotAtBeginning
                        && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getCharacters()
                isScrolling = false
            } else {
                binding.charactersRecyclerview.setPadding(0, 0,0,0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }



    private fun makeCharactersRequest() {
        if (networkManager.checkNetworkAvailability(requireContext())) {
            viewModel.getCharacters()
        }
    }


    private fun setContext() {
        viewModel.characters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        allCharactersAdapter.differ.submitList(it.results.toList())
                        val totalPages = it.count / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.num == totalPages
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(tag, "ERROR! $message")
                    }
                    hideProgressBar()
                }
                is Resource.Empty -> {
                    hideProgressBar()
                }
            }
        }
    }


}