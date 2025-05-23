package com.techadive.movie.usecases.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.movie.repositories.favorites.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetFavoritesUseCaseTest {

    private lateinit var getFavoritesUseCase: GetFavoritesUseCaseImpl

    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getFavoritesUseCase = GetFavoritesUseCaseImpl(mockFavoriteRepository)
    }

    @Test
    fun `when getFavorites is called, then favoriteRepository getFavorites is invoked and returns correct list`() = runTest {
        val expectedFavorites = listOf(
            MovieCardData(
                movieId = 101,
                originalTitle = "Fake Movie",
                releaseDate = "2025-01-01",
                voteAverage = 7.5,
                posterPath = "/poster.jpg",
                overview = "A test movie for unit testing.",
                isFavorite = false
            )
        )

        whenever(mockFavoriteRepository.getFavorites()).thenReturn(expectedFavorites)

        val actualFavorites = getFavoritesUseCase.getFavorites()

        verify(mockFavoriteRepository, times(1)).getFavorites()
        assertEquals(expectedFavorites, actualFavorites)
    }
}
