package com.techadive.movie.viewmodels.search

import com.techadive.common.models.Movie
import com.techadive.common.models.MovieList
import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import com.techadive.movie.usecases.search.SearchMoviesUseCase
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMovieResultsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private lateinit var viewModel: SearchMovieResultsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        searchMoviesUseCase = mock()
        getFavoritesUseCase = mock()

        viewModel = SearchMovieResultsViewModel(searchMoviesUseCase, getFavoritesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun <T> flowOfLoadingAndSuccess(data: T): Flow<AppResult<T>> = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(data))
    }

    private fun <T> flowOfError(): Flow<AppResult<T>> = flow {
        emit(AppResult.Loading)
        emit(AppResult.Error(Exception("Error")))
    }

    @Test
    fun `searchMovies updates UIState correctly on error`() = runTest {
        whenever(getFavoritesUseCase.getFavorites()).thenReturn(emptyList())
        whenever(searchMoviesUseCase.searchMovies(any(), any(), any())).thenReturn(flowOfError())

        viewModel.searchMovies("query")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.searchMovieResultsUIState.value
        assertFalse(state.isLoading)
        assertTrue(state.isError)
        assertEquals(null, state.movieList)
    }

    @Test
    fun `searchMovies resets state when query is null`() = runTest {
        viewModel.searchMovies(null)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.searchMovieResultsUIState.value
        // query and movieList should be reset
        assertEquals(null, state.query)
        assertEquals(null, state.movieList)
        assertFalse(state.isLoading)
        assertFalse(state.isError)
    }
}
