package com.techadive.movie.di

import com.techadive.movie.repositories.MovieRepository
import com.techadive.movie.repositories.MovieRepositoryImpl
import com.techadive.network.api.ApiService
import com.techadive.common.LanguageProviderImpl
import com.techadive.common.LanguageProvider
import com.techadive.data.local.dao.MovieDao
import com.techadive.movie.usecases.GetNowPlayingMoviesUseCase
import com.techadive.movie.usecases.GetNowPlayingMoviesUseCaseImpl
import com.techadive.movie.usecases.GetPopularMoviesUseCase
import com.techadive.movie.usecases.GetPopularMoviesUseCaseImpl
import com.techadive.movie.usecases.GetTopRatedMoviesUseCase
import com.techadive.movie.usecases.GetTopRatedMoviesUseCaseImpl
import com.techadive.movie.usecases.GetUpcomingMoviesUseCase
import com.techadive.movie.usecases.GetUpcomingMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Singleton
    @Provides
    fun providesLanguageProvider(): LanguageProvider =
        LanguageProviderImpl()

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
        languageProvider: LanguageProvider,
        movieDao: MovieDao
    ): MovieRepository =
        MovieRepositoryImpl(
            apiService,
            languageProvider,
            movieDao
        )

    @Provides
    fun provideGetUpcomingMoviesUseCase(
        movieRepository: MovieRepository
    ): GetUpcomingMoviesUseCase =
        GetUpcomingMoviesUseCaseImpl(movieRepository)

    @Provides
    fun provideGetPopularMoviesUseCase(
        movieRepository: MovieRepository
    ): GetPopularMoviesUseCase =
        GetPopularMoviesUseCaseImpl(
            movieRepository
        )

    @Provides
    fun provideGetTopRatedMoviesUseCase(
        movieRepository: MovieRepository
    ): GetTopRatedMoviesUseCase =
        GetTopRatedMoviesUseCaseImpl(
            movieRepository
        )

    @Provides
    fun provideGetNowPlayingMoviesUseCase(
        movieRepository: MovieRepository
    ): GetNowPlayingMoviesUseCase =
        GetNowPlayingMoviesUseCaseImpl(
            movieRepository
        )
}