package com.example.movieexplorer.presentation.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movieexplorer.R
import com.example.movieexplorer.presentation.components.ErrorHolder
import com.example.movieexplorer.presentation.components.LoadingIndicator
import com.example.movieexplorer.presentation.components.RatingInputRow
import com.example.movieexplorer.util.Resource

@Composable
fun MovieDetailScreen(
    movieId: String?,
    onBackClick: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId?.toInt() ?: 0)
    }

    val movieState by viewModel.movieDetails.collectAsStateWithLifecycle()

    when (movieState) {
        is Resource.Loading -> {
            LoadingIndicator()
        }

        is Resource.Success -> {
            val movie = movieState.data
            movie?.let {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "back arrow",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .padding(top = 26.dp, start = 16.dp, end = 16.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .size(44.dp, 43.dp)
                                .clickable { onBackClick() }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = movie.title,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Column {
                            Text(text = "From ${movie.voteCount} users")
                            RatingInputRow(rating = movie.voteAverage)
                        }
                    }
                    Text(
                        text = movie.overview,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )

                }
            }
        }

        is Resource.Error -> {
            ErrorHolder(text = movieState.message ?: "Error loading movie details")
        }

        is Resource.Idle -> {}
    }
}
