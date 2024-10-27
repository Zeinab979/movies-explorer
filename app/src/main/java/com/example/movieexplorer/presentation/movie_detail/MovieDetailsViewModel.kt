package com.example.movieexplorer.presentation.movie_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.usecase.MovieUseCases
import com.example.movieexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases,
) : ViewModel() {
    private val _movieDetails = MutableStateFlow<Resource<Movie>>(Resource.Loading())
    val movieDetails: StateFlow<Resource<Movie>> = _movieDetails
    suspend fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(IO) {
            Log.d("MovieDetailViewModel", "Fetching details for movie ID: $movieId")
            movieUseCases.getMovieDetailsUseCase(movieId).collect { result ->
                Log.d("MovieDetailViewModel", "Movie details: $result")
                _movieDetails.value = result
            }
        }
    }
}