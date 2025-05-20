package com.techadive.movie.di

import com.techadive.movie.repositories.MovieRepository
import com.techadive.movie.repositories.MovieRepositoryImpl
import com.techadive.network.api.ApiService
import com.techadive.common.LanguageProviderImpl
import com.techadive.common.LanguageProvider
import com.techadive.data.local.dao.MovieDao
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
        languageProvider: LanguageProvider,
        apiService: ApiService,
        movieDao: MovieDao
    ): MovieRepository =
        MovieRepositoryImpl(
            apiService,
            languageProvider,
            movieDao
        )
}