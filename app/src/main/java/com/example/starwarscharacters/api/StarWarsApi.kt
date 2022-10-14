package com.example.starwarscharacters.api

import com.example.starwarscharacters.data.Characters
import com.example.starwarscharacters.data.Films
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsApi {

    @GET("people")
    suspend fun getCharactersList(): Response<Characters>

    @GET("people/?search")
    suspend fun getCharacterSearch(
        @Query("name") name: String
    ): Response<Characters>

    @GET
    fun getFilm(@Url url: String): Films

}
