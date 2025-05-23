package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.repositories.movies.MovieRepository
import com.techadive.network.models.convertToMovieList
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
class GetNowPlayingMoviesUseCaseTest {

    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCaseImpl

    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCaseImpl(mockMovieRepository)
    }

    @Test
    fun `when getNowPlayingMovies is called and repository returns success, emits success result`() = runTest {
        val page = 1
        val expectedResult = AppResult.Success(FakeData.movieListDTO().convertToMovieList())

        whenever(mockMovieRepository.getNowPlayingMovies(page)).thenReturn(flowOf(expectedResult))

        val result = getNowPlayingMoviesUseCase.getNowPlayingMovies(page).first()

        assertTrue(result is AppResult.Success)
        assertEquals(expectedResult.data, (result as AppResult.Success).data)
        verify(mockMovieRepository, times(1)).getNowPlayingMovies(page)
    }

    @Test
    fun `when getNowPlayingMovies is called and repository returns error, emits error result`() = runTest {
        val page = 1
        val exception = Exception("Network error")
        val expectedResult = AppResult.Error(exception)

        whenever(mockMovieRepository.getNowPlayingMovies(page)).thenReturn(flowOf(expectedResult))

        val result = getNowPlayingMoviesUseCase.getNowPlayingMovies(page).first()

        assertTrue(result is AppResult.Error)
        verify(mockMovieRepository, times(1)).getNowPlayingMovies(page)
    }
}
