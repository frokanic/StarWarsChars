package com.example.starwarscharacters.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwarscharacters.R
import com.example.starwarscharacters.databinding.ActivityMainBinding
import com.example.starwarscharacters.databinding.FragmentAllCharactersBinding
import com.example.starwarscharacters.databinding.FragmentCharacterBinding
import com.example.starwarscharacters.ui.adapters.AllCharactersAdapter


class AllCharactersFragment : Fragment(R.layout.fragment_all_characters) {

    private lateinit var binding: FragmentAllCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}