package com.example.rickandmorty_api.models

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String
)
