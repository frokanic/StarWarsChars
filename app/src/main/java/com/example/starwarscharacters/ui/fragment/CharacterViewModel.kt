package com.example.starwarscharacters.ui.fragment

import androidx.lifecycle.ViewModel
import com.example.starwarscharacters.repository.StarWarsRepository

class CharacterViewModel(
    private val repository: StarWarsRepository
): ViewModel() {
}