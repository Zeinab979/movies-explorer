
package com.example.movieexplorer.data.mappers

import com.example.movieexplorer.data.model.GenreDto
import com.example.movieexplorer.data.model.MovieDto
import com.example.movieexplorer.domain.model.Genre
import com.example.movieexplorer.domain.model.Movie

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )

}
