package com.techadive.movie.usecases.favorites

import com.techadive.common.models.MovieCardData
import com.techadive.movie.repositories.favorites.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class AddToFavoritesUseCaseTest {

    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCaseImpl

    @Mock
    private lateinit var mockFavoriteRepository: FavoriteRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        addToFavoritesUseCase = AddToFavoritesUseCaseImpl(mockFavoriteRepository)
    }

    @Test
    fun `when addToFavorites is called, then favoriteRepository addToFavorites is invoked with same data`() =
        runTest {
            val favorite = MovieCardData(
                movieId = 101,
                releaseDate = "2025-01-01",
                originalTitle = "Fake Movie",
                voteAverage = 7.5,
                posterPath = "/poster.jpg",
                overview = "A test movie for unit testing.",
                isFavorite = false
            )

            addToFavoritesUseCase.addToFavorites(favorite)

            verify(mockFavoriteRepository, times(1)).addToFavorites(favorite)
        }
}
