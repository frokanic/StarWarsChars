package com.example.starwarscharacters.ui.activity


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarscharacters.common.Resource
import com.example.starwarscharacters.data.Characters
import com.example.starwarscharacters.data.Films
import com.example.starwarscharacters.network.NetworkConnectivityObserver
import com.example.starwarscharacters.repository.StarWarsRepository
import kotlinx.coroutines.launch
import retrofit2.Response


class MainActivityViewModel(
    private val repository: StarWarsRepository
): ViewModel() {

    val characters: MutableLiveData<Resource<Characters>> = MutableLiveData()
    private var charactersResponse: Characters? = null
    val films: MutableLiveData<Resource<Films>> = MutableLiveData()
    private var filmsResponse: Films? = null
    private lateinit var networkConnectivityObserver: NetworkConnectivityObserver
    var num = 1

    fun getCharacters() = viewModelScope.launch {
        characters.postValue(Resource.Loading())
        val response = repository.getCharactersList()
        characters.postValue(handleCharactersResponse(response))
    }

    suspend fun searchCharacters(name: String) {
        characters.postValue(Resource.Loading())
        characters.postValue(Resource.Loading())
        val response = repository.searchCharacters(name)
        characters.postValue(handleCharactersResponse(response))
    }

    suspend fun getFilms(num: String) {
        films.postValue(Resource.Loading())
        films.postValue(Resource.Loading())
        val response = repository.getFilms(num)
        films.postValue(handleFilmsResponse(response))
    }

    private fun handleCharactersResponse(response: Response<Characters>): Resource<Characters> {
        if (response.isSuccessful) {
            response.body()?.let {
                num++
                if (charactersResponse == null) {
                    charactersResponse = it
                } else {
                    val oldCharacters = charactersResponse?.results
                    val newCharacters = it.results
                    if (newCharacters != null) {
                        oldCharacters?.addAll(newCharacters)
                    }
                }
                return Resource.Success(charactersResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleFilmsResponse(response: Response<Films>): Resource<Films> {
        if (response.isSuccessful) {
            response.body()?.let {
                if (filmsResponse == null) {
                    filmsResponse = it
                }
                return Resource.Success(filmsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }


}