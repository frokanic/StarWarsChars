package com.example.starwarscharacters.repository

import com.example.starwarscharacters.data.Characters
import com.example.starwarscharacters.data.Films
import com.example.starwarscharacters.remote.RetrofitInstance
import retrofit2.Response

class StarWarsRepository {

    suspend fun getCharactersList(): Response<Characters> {
        return RetrofitInstance.api.getCharactersList()
    }

    suspend fun searchCharacters(name: String): Response<Characters> {
        return RetrofitInstance.api.getCharacterSearch(name)
    }

    fun getFilms(url: String): Films {
        return RetrofitInstance.api.getFilm(url)
    }

}