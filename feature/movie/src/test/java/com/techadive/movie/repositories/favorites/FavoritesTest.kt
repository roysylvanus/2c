package com.techadive.movie.repositories.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.data.local.dao.FavoritesDao
import com.techadive.data.local.entities.FavoriteEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class FavoriteRepositoryTest {

    private lateinit var favoriteRepository: FavoriteRepositoryImpl

    @Mock
    private lateinit var mockFavoritesDao: FavoritesDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        favoriteRepository = FavoriteRepositoryImpl(mockFavoritesDao)
    }

    @Test
    fun `when getFavorites is called, returns list of MovieCardData`() = runTest {
        val favoriteEntities = listOf(
            FavoriteEntity(
                movieId = 1,
                releaseDate = "2025-01-01",
                originalTitle = "Test Movie",
                voteAverage = 7.5,
                posterPath = "/poster.jpg",
                overview = "Test overview"
            )
        )
        `when`(mockFavoritesDao.getFavorites()).thenReturn(favoriteEntities)

        val result = favoriteRepository.getFavorites()

        assertEquals(1, result.size)
        assertEquals("Test Movie", result[0].originalTitle)
        verify(mockFavoritesDao, times(1)).getFavorites()
    }

    @Test
    fun `when addToFavorites is called, adds FavoriteEntity to dao`() = runTest {
        val movieCard = MovieCardData(
            movieId = 2,
            releaseDate = "2025-02-01",
            originalTitle = "Another Movie",
            voteAverage = 8.0,
            posterPath = "/poster2.jpg",
            overview = "Another overview",
            isFavorite = false
        )

        favoriteRepository.addToFavorites(movieCard)

        verify(mockFavoritesDao, times(1)).addToFavorites(
            FavoriteEntity(
                movieId = movieCard.movieId,
                releaseDate = movieCard.releaseDate,
                originalTitle = movieCard.originalTitle,
                voteAverage = movieCard.voteAverage,
                posterPath = movieCard.posterPath,
                overview = movieCard.overview
            )
        )
    }

    @Test
    fun `when removeFromFavorites is called with movie id, removes favorite from dao`() = runTest {
        val movieId = 3

        favoriteRepository.removeFromFavorites(movieId)

        verify(mockFavoritesDao, times(1)).removeFromFavorites(movieId)
    }

    @Test
    fun `when isFavorite is called with movie id, returns dao result`() = runTest {
        val movieId = 4
        `when`(mockFavoritesDao.isFavorite(movieId)).thenReturn(1)

        val result = favoriteRepository.isFavorite(movieId)

        assertEquals(1, result)
        verify(mockFavoritesDao, times(1)).isFavorite(movieId)
    }
}
