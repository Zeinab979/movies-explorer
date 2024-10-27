package com.example.movieexplorer.presentation.movie_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieexplorer.R
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.ui.theme.MovieExplorerTheme

@Composable
fun MovieItem(movie: Movie, onClick: (Movie) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick(movie) }
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${movie.posterUrl}")
                .crossfade(true)
                .placeholder(R.drawable.baseline_broken_image_24)
                .error(R.drawable.error)
                .build(),
            contentDescription = movie.title,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {

    MovieExplorerTheme {
        MovieItem(
            movie = Movie(
                id = 1,
                title = "Movie Title",
                posterUrl = " ",
                releaseDate = " ",
                overview = "Movie Overview",
                voteAverage = 0.0,
                voteCount = 0
            ),
            onClick = {}
        )
    }

}

