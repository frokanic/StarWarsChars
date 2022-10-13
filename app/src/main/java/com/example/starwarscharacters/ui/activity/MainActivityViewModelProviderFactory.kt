package com.example.starwarscharacters.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starwarscharacters.repository.StarWarsRepository


class MainActivityViewModelProviderFactory(
    private val repository: StarWarsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}