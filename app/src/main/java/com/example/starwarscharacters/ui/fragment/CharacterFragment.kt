package com.example.starwarscharacters.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarscharacters.R
import com.example.starwarscharacters.data.ResultCharacters
import com.example.starwarscharacters.databinding.FragmentCharacterBinding
import com.example.starwarscharacters.ui.activity.MainActivityViewModel
import com.example.starwarscharacters.ui.adapters.CharacterAdapter


class CharacterFragment : Fragment(R.layout.fragment_character) {

    private lateinit var binding: FragmentCharacterBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var character: ResultCharacters
    private val args by navArgs<CharacterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpContext()
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

