package com.example.rickandmorty_api.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty_api.models.Character
import com.example.rickandmorty_api.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CharacterDetailViewModel : ViewModel() {

    private val _characterDetail = MutableStateFlow<ApiResult<Character?>>(ApiResult.Loading)
    val characterDetail: StateFlow<ApiResult<Character?>> get() = _characterDetail

    fun getCharacterDetail(characterName: String) {
        _characterDetail.value = ApiResult.Loading
        viewModelScope.launch {

            ApiClient.api.getCharacterDetailByName(characterName).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    if (response.isSuccessful) {

                        val character = response.body()
                        if (character != null) {
                            _characterDetail.value = ApiResult.success(character)
                        } else {
                            _characterDetail.value = ApiResult.error("Character not found")
                        }
                    } else {

                        _characterDetail.value = ApiResult.error("Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {

                    _characterDetail.value = ApiResult.error("Network Error: ${t.message ?: "Unknown Error"}")
                    Log.e("CharacterDetailViewModel", "Error: ${t.message}")
                }
            })
        }
    }
}


sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()

    companion object {
        fun <T> success(data: T): ApiResult<T> = Success(data)
        fun error(message: String): ApiResult<Nothing> = Error(message)
        fun loading(): ApiResult<Nothing> = Loading
    }
}