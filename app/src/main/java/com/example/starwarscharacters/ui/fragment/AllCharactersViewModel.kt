package com.example.starwarscharacters.ui.fragment

import androidx.lifecycle.ViewModel
import com.example.starwarscharacters.repository.StarWarsRepository

class AllCharactersViewModel(
    private val repository: StarWarsRepository
): ViewModel() {
}