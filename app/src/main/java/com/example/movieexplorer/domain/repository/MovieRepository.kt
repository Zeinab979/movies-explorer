package com.example.movieexplorer.domain.repository

import com.example.movieexplorer.domain.model.Genre
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getGenres(): Flow<Resource<List<Genre>>>
    suspend fun getMoviesByGenre(genreId: String): Flow<Resource<List<Movie>>>
    suspend fun searchMovies( query: String): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<Movie>>
}