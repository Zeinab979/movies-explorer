package com.example.movieexplorer.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<MovieDto>,
)

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,

)