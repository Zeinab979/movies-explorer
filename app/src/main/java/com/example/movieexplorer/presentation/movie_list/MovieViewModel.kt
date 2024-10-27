package com.example.movieexplorer.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.domain.model.Genre
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.domain.usecase.MovieUseCases
import com.example.movieexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(kotlinx.coroutines.FlowPreview::class)
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCases: MovieUseCases,
) : ViewModel() {

    private val _genres = MutableStateFlow<Resource<List<Genre>>>(Resource.Idle())
    val genres: StateFlow<Resource<List<Genre>>> = _genres
    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Idle())
    val movies: StateFlow<Resource<List<Movie>>> = _movies
    private val _searchQuery = MutableStateFlow("")
    private val movieCache = mutableMapOf<Int, List<Movie>>()
    private val searchCache = mutableMapOf<String, List<Movie>>()
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching
    init {
        getGenres()
        setupSearchListener()
    }
    private fun setupSearchListener() {
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotEmpty()) searchMovies(query)
            }
            .launchIn(viewModelScope)
    }
    fun getGenres() {
        viewModelScope.launch(IO) {
            movieUseCases.getGenresUseCase().collect { result ->
                _genres.value = result
            }
        }
    }
    fun getMoviesByGenre(genreId: Int) {
        val cachedMovies = movieCache[genreId]
        if (cachedMovies != null) {
            _movies.value = Resource.Success(cachedMovies)
            return
        }

        viewModelScope.launch {
            _movies.value = Resource.Loading()
            movieUseCases.getMoviesByGenreUseCase(genreId.toString()).collect { result ->
                if (result is Resource.Success) {
                    movieCache[genreId] = result.data ?: emptyList()
                }
                _movies.value = result
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun searchMovies(query: String) {
        searchCache[query]?.let {
            _movies.value = Resource.Success(it)
            return
        }
        _isSearching.value = true
        _movies.value = Resource.Loading()
        viewModelScope.launch {
            movieUseCases.searchMoviesUseCase(query).collect { result ->
                if (result is Resource.Success) searchCache[query] = result.data ?: emptyList()
                _movies.value = result
                _isSearching.value = false
            }
        }
    }
}
