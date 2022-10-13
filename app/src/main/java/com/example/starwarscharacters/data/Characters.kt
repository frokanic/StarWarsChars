package com.example.starwarscharacters.data

data class Characters(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: MutableList<ResultCharacters>
)