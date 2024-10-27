package com.example.movieexplorer.data.repository

import com.example.movieexplorer.data.mappers.toDomain
import com.example.movieexplorer.data.remote.MovieApi
import com.example.movieexplorer.domain.model.Genre
import com.example.movieexplorer.domain.repository.MovieRepository
import com.example.movieexplorer.util.Resource
import com.example.movieexplorer.domain.model.Movie
import com.example.movieexplorer.util.Constant
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.retryWhen
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApi
) : MovieRepository {

    override suspend fun getGenres(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getGenres(Constant.ApiKey)
            val genres = response.genres.map { it.toDomain() }
            emit(Resource.Success(genres))
        } catch (e: HttpException) {
            val message = handleHttpException(e)
            emit(Resource.Error("HTTP error: $message"))
        }
    }.retryPolicy()

    override suspend fun getMoviesByGenre(genreId: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getMoviesByGenre(Constant.ApiKey, genreId)
            val movies = response.results.map { it.toDomain() }
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            val message = handleHttpException(e)
            emit(Resource.Error("HTTP error: $message"))
        }
    }.retryPolicy()

    override suspend fun searchMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.searchMovies(Constant.ApiKey, query)
            val movies = response.results.map { it.toDomain() }
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            val message = handleHttpException(e)
            emit(Resource.Error("HTTP error: $message"))
        }
    }.retryPolicy()

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getMovieDetails(movieId, Constant.ApiKey)
            val movieDetails = response.toDomain()
            emit(Resource.Success(movieDetails))
        } catch (e: HttpException) {
            val message = handleHttpException(e)
            emit(Resource.Error("HTTP error: $message"))
        }
    }.retryPolicy()


    private fun handleHttpException(e: HttpException): String {
        return when (e.code()) {
            401 -> "Unauthorized access. Please check your API key."
            404 -> "Requested data not found."
            500 -> "Server error. Please try again later."
            else -> "An unexpected error occurred: ${e.localizedMessage} (code: ${e.code()})"
        }
    }

    private fun <T> Flow<Resource<T>>.retryPolicy(
        maxRetries: Int = 3,
        initialDelay: Duration = 2.seconds,
        maxDelay: Duration = 8.seconds
    ): Flow<Resource<T>> = retryWhen { cause, attempt ->
        if (cause is IOException && attempt < maxRetries) {
            val delayDuration = (initialDelay.inWholeMilliseconds * (2.0.pow(attempt.toDouble()))).toLong()
            val cappedDelay = minOf(delayDuration, maxDelay.inWholeMilliseconds)
            delay(cappedDelay)
            true
        } else {
            false
        }
    }
}


