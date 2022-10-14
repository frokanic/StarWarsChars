package com.example.starwarscharacters.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultCharacters(
    @SerializedName("birth_year")
    val birth_year: String,
    val created: String,
    val edited: String,
    val eye_color: String,
    @SerializedName("films")
    val films: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    @SerializedName("name")
    val name: String,
    val skin_color: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
): Parcelable