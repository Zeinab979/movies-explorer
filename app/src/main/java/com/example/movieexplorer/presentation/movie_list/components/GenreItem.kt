package com.example.movieexplorer.presentation.movie_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieexplorer.domain.model.Genre
import com.example.movieexplorer.ui.theme.MovieExplorerTheme

@Composable
fun GenreItem(genre: Genre, isSelected: Boolean, onClick: () -> Unit) {
        Text(
            text = genre.name,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(7.dp))
                .border(if (isSelected)0.dp else 0.7.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                .clickable { onClick() }
                .padding(8.dp)
                .wrapContentHeight(),
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp

        )
    }

@Preview(showBackground =true )
@Composable
private fun GenreItemPreview() {
    MovieExplorerTheme {
        GenreItem(genre = Genre(1, "Action"), isSelected = true) {}
    }
}
@Preview(showBackground =true )
@Composable
private fun GenreItemPreview2() {
    MovieExplorerTheme {
        GenreItem(genre = Genre(1, "Action"), isSelected = false) {}
    }}