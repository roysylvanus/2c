package com.techadive.movie.viewmodels.home

import com.techadive.common.models.Movie
import com.techadive.common.models.MovieCardData
import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.usecases.favorites.GetFavoritesUseCase
import com.techadive.movie.usecases.movies.GetNowPlayingMoviesUseCase
import com.techadive.movie.usecases.movies.GetPopularMoviesUseCase
import com.techadive.movie.usecases.movies.GetTopRatedMoviesUseCase
import com.techadive.movie.usecases.movies.GetUpcomingMoviesUseCase
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getUpcomingMoviesUseCase = mock()
        getPopularMoviesUseCase = mock()
        getTopRatedMoviesUseCase = mock()
        getNowPlayingMoviesUseCase = mock()
        getFavoritesUseCase = mock()

        viewModel = HomeViewModel(
            getUpcomingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getNowPlayingMoviesUseCase,
            getFavoritesUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun <T> flowOfSuccess(data: T): Flow<AppResult<T>> = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(data))
    }

    @Test
    fun `fetchHomeViewData updates UIState with movie lists and favorites`() = runTest {
        val favoriteMovieIds = listOf(1, 2)
        val movie1 = FakeData.movieListDTO().convertToMovieList().results[0]
        val movie2 = FakeData.movieListDTO().convertToMovieList().results[0]
        val movie3 = FakeData.movieListDTO().convertToMovieList().results[0]

        val movieListWithFavorites = FakeData.movieListDTO().convertToMovieList()

        whenever(getFavoritesUseCase.getFavorites()).thenReturn(
            favoriteMovieIds.map { id ->
                MovieCardData(
                    movieId = id,
                    originalTitle = "title$id",
                    isFavorite = true,
                    releaseDate = "",
                    voteAverage = 0.0,
                    posterPath = "",
                    overview = ""
                )
            }
        )
        whenever(getUpcomingMoviesUseCase.getUpcomingMovies(any())).thenReturn(flowOfSuccess(movieListWithFavorites))
        whenever(getPopularMoviesUseCase.getPopularMovies(any())).thenReturn(flowOfSuccess(movieListWithFavorites))
        whenever(getTopRatedMoviesUseCase.getTopRatedMovies(any())).thenReturn(flowOfSuccess(movieListWithFavorites))
        whenever(getNowPlayingMoviesUseCase.getNowPlayingMovies(any())).thenReturn(flowOfSuccess(movieListWithFavorites))

        viewModel.fetchHomeViewData(isRefresh = true)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeUIState.value

        // Check refreshing state is reset
        assertFalse(state.isRefreshing)

        // Verify favorite flags are set for upcoming movies
        val upcomingMovies = state.upcomingState.data?.results ?: emptyList()
        assertTrue(upcomingMovies.any { it.id == 1 && it.isFavorite })
        assertTrue(upcomingMovies.any { it.id == 2 && it.isFavorite })
        assertTrue(upcomingMovies.any { it.id == 3 && !it.isFavorite })

        // Similarly verify popularState
        val popularMovies = state.popularState.data?.results ?: emptyList()
        assertTrue(popularMovies.any { it.id == 1 && it.isFavorite })
        assertTrue(popularMovies.any { it.id == 2 && it.isFavorite })
        assertTrue(popularMovies.any { it.id == 3 && !it.isFavorite })

        // Verify topRatedState
        val topRatedMovies = state.topRatedState.data?.results ?: emptyList()
        assertTrue(topRatedMovies.any { it.id == 1 && it.isFavorite })
        assertTrue(topRatedMovies.any { it.id == 2 && it.isFavorite })
        assertTrue(topRatedMovies.any { it.id == 3 && !it.isFavorite })

        // Verify nowPlayingState
        val nowPlayingMovies = state.nowPlayingState.data?.results ?: emptyList()
        assertTrue(nowPlayingMovies.any { it.id == 1 && it.isFavorite })
        assertTrue(nowPlayingMovies.any { it.id == 2 && it.isFavorite })
        assertTrue(nowPlayingMovies.any { it.id == 3 && !it.isFavorite })

        // Verify use case calls
        verify(getFavoritesUseCase).getFavorites()
        verify(getUpcomingMoviesUseCase).getUpcomingMovies(any())
        verify(getPopularMoviesUseCase).getPopularMovies(any())
        verify(getTopRatedMoviesUseCase).getTopRatedMovies(any())
        verify(getNowPlayingMoviesUseCase).getNowPlayingMovies(any())
    }
}