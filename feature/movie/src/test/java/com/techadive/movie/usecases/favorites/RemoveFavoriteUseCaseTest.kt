package com.techadive.movie.usecases.favorites

import com.techadive.movie.repositories.favorites.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.Test

@ExperimentalCoroutinesApi
class RemoveFavoriteUseCaseTest {

    private lateinit var removeFavoriteUseCase: RemoveFavoriteUseCaseImpl

    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        removeFavoriteUseCase = RemoveFavoriteUseCaseImpl(mockFavoriteRepository)
    }

    @Test
    fun `when removeFromFavorites is called with valid id, then favoriteRepository removeFromFavorites is invoked`() = runTest {
        val movieId = 101

        removeFavoriteUseCase.removeFromFavorites(movieId)

        verify(mockFavoriteRepository, times(1)).removeFromFavorites(movieId)
    }
}
