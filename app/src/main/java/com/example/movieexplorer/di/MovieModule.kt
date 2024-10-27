package com.example.movieexplorer.di

import com.example.movieexplorer.data.remote.MovieApi
import com.example.movieexplorer.data.repository.MovieRepositoryImpl
import com.example.movieexplorer.domain.repository.MovieRepository
import com.example.movieexplorer.domain.usecase.GetGenresUseCase
import com.example.movieexplorer.domain.usecase.GetMovieDetailsUseCase
import com.example.movieexplorer.domain.usecase.GetMoviesByGenreUseCase
import com.example.movieexplorer.domain.usecase.MovieUseCases
import com.example.movieexplorer.domain.usecase.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideRetrofit(): MovieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetGenresUseCase(repository: MovieRepository): GetGenresUseCase {
        return GetGenresUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMoviesByGenreUseCase(repository: MovieRepository): GetMoviesByGenreUseCase {
        return GetMoviesByGenreUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(repository: MovieRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideMovieUseCases(
        getGenresUseCase: GetGenresUseCase,
        getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
        searchMoviesUseCase: SearchMoviesUseCase,
        getMovieDetailsUseCase: GetMovieDetailsUseCase,
    ): MovieUseCases {
        return MovieUseCases(
            getGenresUseCase = getGenresUseCase,
            getMoviesByGenreUseCase = getMoviesByGenreUseCase,
            searchMoviesUseCase = searchMoviesUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase
        )
    }
}
