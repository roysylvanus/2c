package com.techadive.movie.usecases.favorites

import com.techadive.movie.repositories.favorites.FavoriteRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class CheckIfMovieIsFavoriteUseCaseTest {

    private lateinit var checkIfMovieIsFavoriteUseCase: CheckIfMovieIsFavoriteUseCaseImpl

    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        checkIfMovieIsFavoriteUseCase = CheckIfMovieIsFavoriteUseCaseImpl(mockFavoriteRepository)
    }

    @Test
    fun `when isFavorite is called, then favoriteRepository isFavorite is invoked and returns correct result`() = runTest {
        val movieId = 101
        val expectedResult = 1

        whenever(mockFavoriteRepository.isFavorite(movieId)).thenReturn(expectedResult)

        val actualResult = checkIfMovieIsFavoriteUseCase.isFavorite(movieId)

        verify(mockFavoriteRepository, times(1)).isFavorite(movieId)
        assertEquals(expectedResult, actualResult)
    }
}
