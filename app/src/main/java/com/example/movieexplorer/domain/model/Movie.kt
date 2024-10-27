package com.example.movieexplorer.domain.model


data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
)




