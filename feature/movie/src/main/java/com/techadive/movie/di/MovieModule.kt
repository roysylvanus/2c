package com.techadive.movie.di

import com.techadive.movie.repositories.movies.MovieRepository
import com.techadive.movie.repositories.movies.MovieRepositoryImpl
import com.techadive.network.api.ApiService
import com.techadive.common.providers.LanguageProviderImpl
import com.techadive.common.providers.LanguageProvider
import com.techadive.data.local.dao.FavoritesDao
import com.techadive.data.local.dao.MovieDao
import com.techadive.data.local.dao.SearchDao
import com.techadive.movie.repositories.favorites.FavoriteRepository
import com.techadive.movie.repositories.favorites.FavoriteRepositoryImpl
import com.techadive.movie.repositories.search.SearchRepository
import com.techadive.movie.repositories.search.SearchRepositoryImpl
import com.techadive.movie.usecases.favorites.AddToFavoritesUseCase
import com.techadive.movie.usecases.favorites.AddToFavoritesUseCaseImpl
import com.techadive.movie.usecases.favorites.CheckIfMovieIsFavoriteUseCase
import com.techadive.movie.usecases.favorites.CheckIfMovieIsFavoriteUseCaseImpl
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import com.techadive.movie.usecases.favorites.GetFavoritesUseCaseImpl
import com.techadive.movie.usecases.favorites.RemoveFavoriteUseCase
import com.techadive.movie.usecases.favorites.RemoveFavoriteUseCaseImpl
import com.techadive.movie.usecases.movies.GetMovieDetailsUseCase
import com.techadive.movie.usecases.movies.GetMovieDetailsUseCaseImpl
import com.techadive.movie.usecases.movies.GetNowPlayingMoviesUseCase
import com.techadive.movie.usecases.movies.GetNowPlayingMoviesUseCaseImpl
import com.techadive.movie.usecases.movies.GetPopularMoviesUseCase
import com.techadive.movie.usecases.movies.GetPopularMoviesUseCaseImpl
import com.techadive.movie.usecases.movies.GetRecommendedMoviesUseCase
import com.techadive.movie.usecases.movies.GetRecommendedMoviesUseCaseImpl
import com.techadive.movie.usecases.movies.GetTopRatedMoviesUseCase
import com.techadive.movie.usecases.movies.GetTopRatedMoviesUseCaseImpl
import com.techadive.movie.usecases.movies.GetUpcomingMoviesUseCase
import com.techadive.movie.usecases.movies.GetUpcomingMoviesUseCaseImpl
import com.techadive.movie.usecases.search.DeleteAllRecentSearchHistoryUseCase
import com.techadive.movie.usecases.search.DeleteAllRecentSearchHistoryUseCaseImpl
import com.techadive.movie.usecases.search.GetRecentSearchHistoryUseCase
import com.techadive.movie.usecases.search.GetRecentSearchHistoryUseCaseImpl
import com.techadive.movie.usecases.search.SearchKeywordUseCase
import com.techadive.movie.usecases.search.SearchKeywordUseCaseImpl
import com.techadive.movie.usecases.search.SearchMoviesUseCase
import com.techadive.movie.usecases.search.SearchMoviesUseCaseImpl
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

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchDao: SearchDao,
        apiService: ApiService,
        languageProvider: LanguageProvider
    ): SearchRepository =
        SearchRepositoryImpl(
            searchDao,
            apiService,
            languageProvider
        )

    @Provides
    fun provideDeleteAllRecentSearchHistoryUseCase(searchRepository: SearchRepository): DeleteAllRecentSearchHistoryUseCase =
        DeleteAllRecentSearchHistoryUseCaseImpl(searchRepository)

    @Provides
    fun provideGetRecentSearchHistoryUseCase(searchRepository: SearchRepository): GetRecentSearchHistoryUseCase =
        GetRecentSearchHistoryUseCaseImpl(searchRepository)

    @Provides
    fun provideSearchMoviesUseCase(searchRepository: SearchRepository): SearchMoviesUseCase =
        SearchMoviesUseCaseImpl(searchRepository)

    @Provides
    fun provideSSearchKeywordUseCase(searchRepository: SearchRepository): SearchKeywordUseCase =
        SearchKeywordUseCaseImpl(searchRepository)

    @Provides
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCaseImpl(movieRepository)

    @Provides
    fun provideGetRecommendedMoviesUseCase(movieRepository: MovieRepository): GetRecommendedMoviesUseCase =
        GetRecommendedMoviesUseCaseImpl(movieRepository)

    @Singleton
    @Provides
    fun provideFavoriteRepository(favoritesDao: FavoritesDao): FavoriteRepository =
        FavoriteRepositoryImpl(favoritesDao)

    @Provides
    fun provideAddToFavoritesUseCase(favoriteRepository: FavoriteRepository): AddToFavoritesUseCase =
        AddToFavoritesUseCaseImpl(favoriteRepository)

    @Provides
    fun provideCheckIfMovieIsFavoriteUseCase(favoriteRepository: FavoriteRepository): CheckIfMovieIsFavoriteUseCase =
        CheckIfMovieIsFavoriteUseCaseImpl(favoriteRepository)

    @Provides
    fun provideGetFavoritesUseCase(favoriteRepository: FavoriteRepository): GetFavoritesUseCase =
        GetFavoritesUseCaseImpl(favoriteRepository)

    @Provides
    fun provideRemoveFavoriteUseCase(favoriteRepository: FavoriteRepository): RemoveFavoriteUseCase =
        RemoveFavoriteUseCaseImpl(favoriteRepository)
}