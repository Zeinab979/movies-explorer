package com.example.movieexplorer.domain.usecase

import com.example.movieexplorer.domain.model.Genre
import com.example.movieexplorer.domain.repository.MovieRepository
import com.example.movieexplorer.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Genre>>> {
        return repository.getGenres()
    }
}


