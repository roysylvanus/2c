package com.techadive.movie.viewmodels.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFavoritesUseCase = mock()
        viewModel = FavoritesViewModel(getFavoritesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchFavorites is called then favoritesUIState emits favorites with isFavorite true`() =
        runTest {
            // Arrange
            val fakeFavorites = listOf(
                MovieCardData(
                    movieId = 1,
                    originalTitle = "Movie 1",
                    isFavorite = false,
                    releaseDate = "",
                    voteAverage = 7.5,
                    posterPath = "",
                    overview = ""
                ),
                MovieCardData(
                    movieId = 2,
                    originalTitle = "Movie 2",
                    isFavorite = false,
                    releaseDate = "",
                    voteAverage = 7.5,
                    posterPath = "",
                    overview = ""
                ),
            )
            whenever(getFavoritesUseCase.getFavorites()).thenReturn(fakeFavorites)

            // Act
            viewModel.fetchFavorites()
            testDispatcher.scheduler.advanceUntilIdle()

            // Assert
            val expectedFavorites = fakeFavorites.map { it.copy(isFavorite = true) }
            val actualFavorites = viewModel.favoritesUIState.value.favorites

            assertEquals(expectedFavorites, actualFavorites)
            verify(getFavoritesUseCase).getFavorites()
        }
}
