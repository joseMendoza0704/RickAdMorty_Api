package com.example.rickandmorty_api.models

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)




data class LocationList(
    val results: List<Location>
)
