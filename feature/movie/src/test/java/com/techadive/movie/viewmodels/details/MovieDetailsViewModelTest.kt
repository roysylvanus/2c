package com.techadive.movie.viewmodels.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.usecases.favorites.AddToFavoritesUseCase
import com.techadive.movie.usecases.favorites.CheckIfMovieIsFavoriteUseCase
import com.techadive.movie.usecases.favorites.RemoveFavoriteUseCase
import com.techadive.movie.usecases.movies.GetMovieDetailsUseCase
import com.techadive.movie.usecases.movies.GetRecommendedMoviesUseCase
import com.techadive.network.models.convertToMovieDetails
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // For LiveData/StateFlow

    @Mock
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    private lateinit var getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase

    @Mock
    private lateinit var checkIfMovieIsFavoriteUseCase: CheckIfMovieIsFavoriteUseCase

    @Mock
    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCase

    @Mock
    private lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    private lateinit var viewModel: MovieDetailsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = MovieDetailsViewModel(
            getMovieDetailsUseCase,
            getRecommendedMoviesUseCase,
            checkIfMovieIsFavoriteUseCase,
            addToFavoritesUseCase,
            removeFavoriteUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `whenever FetchMovieDetail event is triggered with valid movieId, updates UI state with movie details and recommended movies`() = runTest {
        val movieId = 101
        val movieDetails = FakeData.movieDetailsDTO().convertToMovieDetails() // Your mapper
        val movieList = FakeData.movieListDTO().convertToMovieList()

        // Stub use cases
        whenever(getMovieDetailsUseCase.getMovieDetails(movieId)).thenReturn(
            flowOf(AppResult.Loading, AppResult.Success(movieDetails))
        )
        whenever(getRecommendedMoviesUseCase.getRecommendedMovies(movieId, 1)).thenReturn(
            flowOf(AppResult.Success(movieList))
        )
        whenever(checkIfMovieIsFavoriteUseCase.isFavorite(movieId)).thenReturn(1)

        viewModel.onEvent(MovieDetailsViewModel.MovieDetailsEvent.FetchMovieDetail(movieId))

        // Advance coroutines to collect all emissions
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.movieDetailsUIState.value
        assertFalse(state.isLoading)
        assertFalse(state.isError)
        assertEquals(movieDetails, state.movieDetails)
        assertEquals(movieList, state.movieList)
        assertTrue(state.movieDetails?.isFavorite == true)
    }

    @Test
    fun `whenever FetchMovieDetail event is triggered with null movieId, emits error state`() = runTest {
        viewModel.onEvent(MovieDetailsViewModel.MovieDetailsEvent.FetchMovieDetail(null))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.movieDetailsUIState.value
        assertTrue(state.isError)
        assertNull(state.movieDetails)
    }
}
