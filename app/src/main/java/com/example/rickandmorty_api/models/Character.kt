package com.example.rickandmorty_api.models

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

data class CharacterList(
    val results: List<Character>
)