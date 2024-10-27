package com.example.movieexplorer.domain.usecase

import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.repository.MovieRepository
import com.example.movieexplorer.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId :Int): Flow<Resource<Movie>>{
        return repository.getMovieDetails(movieId)
    }
}