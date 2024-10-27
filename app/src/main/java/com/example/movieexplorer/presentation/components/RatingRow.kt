package com.example.movieexplorer.presentation.components

import android.content.res.ColorStateList
import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RatingInputRow(
    rating: Double,
) {
    AndroidView(
        factory = { context ->
            RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
                stepSize = 0.5f
                setIsIndicator(true)
                numStars = 5
                progressTintList = ColorStateList.valueOf(Color(0xFFE7C825).toArgb())
            }
        },
        update = { ratingBar ->
            ratingBar.rating = rating.toFloat()

        }
    )
}

@Preview
@Composable
private fun RatingInputRowPreview() {
    RatingInputRow(rating = 3.0)

}