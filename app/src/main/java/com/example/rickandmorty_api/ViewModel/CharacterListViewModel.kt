package com.example.rickandmorty_api.ViewModel

import androidx.lifecycle.ViewModel
import com.example.rickandmorty_api.models.Character
import com.example.rickandmorty_api.models.CharacterList
import com.example.rickandmorty_api.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterListViewModel : ViewModel() {

    private val _characters = MutableStateFlow<Result<List<Character>>>(Result.Loading)
    val characters: StateFlow<Result<List<Character>>> get() = _characters

    fun getCharacters() {
        _characters.value = Result.Loading
        ApiClient.api.getCharacters().enqueue(object : Callback<CharacterList> {
            override fun onResponse(call: Call<CharacterList>, response: Response<CharacterList>) {
                if (response.isSuccessful) {
                    _characters.value = Result.success(response.body()?.results ?: emptyList())
                } else {
                    _characters.value = Result.error("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                _characters.value = Result.error(t.message ?: "Unknown Error")
            }
        })
    }

    fun searchCharacterByName(name: String) {
        _characters.value = Result.Loading
        ApiClient.api.searchCharactersByName(name).enqueue(object : Callback<CharacterList> {
            override fun onResponse(call: Call<CharacterList>, response: Response<CharacterList>) {
                if (response.isSuccessful) {
                    _characters.value = Result.success(response.body()?.results ?: emptyList())
                } else {
                    _characters.value = Result.error("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                _characters.value = Result.error(t.message ?: "Unknown Error")
            }
        })
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun error(message: String): Result<Nothing> = Error(message)
        fun loading(): Result<Nothing> = Loading
    }
}