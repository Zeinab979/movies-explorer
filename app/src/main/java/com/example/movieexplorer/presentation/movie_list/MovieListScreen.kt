package com.example.movieexplorer.presentation.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieexplorer.R
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.presentation.components.ErrorHolder
import com.example.movieexplorer.presentation.components.LoadingIndicator
import com.example.movieexplorer.presentation.movie_list.components.GenreItem
import com.example.movieexplorer.presentation.movie_list.components.MovieItem
import com.example.movieexplorer.util.Resource

@Composable
fun MovieListScreen(
    onMovieClick: (Movie) -> Unit,
) {
    val viewModel: MovieViewModel = hiltViewModel()
    val movieState by viewModel.movies.collectAsStateWithLifecycle()
    val genreState by viewModel.genres.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedGenre by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(Unit) {
        viewModel.getGenres()

    }
    LaunchedEffect(selectedGenre) {
        selectedGenre?.let {
            viewModel.getMoviesByGenre(it)
        }
    }
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 48.dp)
    ) {
        Text(
            text = stringResource(R.string.search_for_a_content),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                if (!isSearching) {
                    searchQuery = query
                    if (query.text.isNotEmpty()) {
                        viewModel.updateSearchQuery(query.text)
                        selectedGenre = null
                    } else if (selectedGenre != null) {
                        viewModel.getMoviesByGenre(selectedGenre!!)
                    }
                }
            },
            singleLine = true,
            label = { Text(stringResource(R.string.search_for_a_content)) },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large

        )
        Spacer(modifier = Modifier.height(27.dp))
        Text(
            text = stringResource(R.string.categories),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        when (genreState) {
            is Resource.Loading -> LoadingIndicator()
            is Resource.Success -> {
                LazyRow {
                    items(genreState.data.orEmpty()) { genre ->
                        GenreItem(genre = genre, isSelected = selectedGenre == genre.id) {
                            selectedGenre = genre.id
                            searchQuery = TextFieldValue("")
                        }
                    }
                }
            }

            is Resource.Error ->ErrorHolder(text = genreState.message ?: "Error loading genres")
            is Resource.Idle -> {}
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.most_searched),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        when (movieState) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(movieState.data ?: emptyList()) { movie ->
                        MovieItem(movie = movie, onClick = { onMovieClick(movie) })
                    }
                }
            }

            is Resource.Error -> ErrorHolder(text = movieState.message ?: "Error loading movies")
            is Resource.Idle -> {}
        }
    }
}


