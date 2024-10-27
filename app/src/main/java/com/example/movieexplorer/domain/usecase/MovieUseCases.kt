package com.example.movieexplorer.domain.usecase
import javax.inject.Inject

data class MovieUseCases @Inject constructor(
    val getGenresUseCase: GetGenresUseCase,
    val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase
)
