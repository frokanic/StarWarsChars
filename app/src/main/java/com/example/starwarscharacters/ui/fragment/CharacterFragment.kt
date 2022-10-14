package com.example.starwarscharacters.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarscharacters.R
import com.example.starwarscharacters.data.Films
import com.example.starwarscharacters.data.ResultCharacters
import com.example.starwarscharacters.databinding.FragmentCharacterBinding
import com.example.starwarscharacters.ui.activity.MainActivity
import com.example.starwarscharacters.ui.activity.MainActivityViewModel
import com.example.starwarscharacters.ui.adapters.CharacterAdapter


class CharacterFragment : Fragment(R.layout.fragment_character) {

    private lateinit var binding: FragmentCharacterBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var character: ResultCharacters
    private lateinit var filmList: ArrayList<Films>
    private val args by navArgs<CharacterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpContext()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            filmList = viewModel.getFilmData()
        }
    }

    fun setUpContext() {
        binding.textViewFullNameValue.text = args.character.name

        characterAdapter = CharacterAdapter()
        binding.recyclerViewFilms.apply {
            adapter = CharacterAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}

