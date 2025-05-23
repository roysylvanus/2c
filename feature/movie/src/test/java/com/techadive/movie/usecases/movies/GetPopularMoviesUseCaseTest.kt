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
class GetPopularMoviesUseCaseTest {

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCaseImpl

    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPopularMoviesUseCase = GetPopularMoviesUseCaseImpl(mockMovieRepository)
    }

    @Test
    fun `when getPopularMovies is called and repository returns success, emits success result`() =
        runTest {
            val page = 1
            val expectedResult = AppResult.Success(FakeData.movieListDTO().convertToMovieList())

            whenever(mockMovieRepository.getPopularMovies(page)).thenReturn(flowOf(expectedResult))

            val result = getPopularMoviesUseCase.getPopularMovies(page).first()

            assertTrue(result is AppResult.Success)
            assertEquals(expectedResult.data, (result as AppResult.Success).data)
            verify(mockMovieRepository, times(1)).getPopularMovies(page)
        }

    @Test
    fun `when getPopularMovies is called and repository returns error, emits error result`() = runTest {
        val page = 1
        val exception = Exception("Network error")
        val expectedResult = AppResult.Error(exception)

        whenever(mockMovieRepository.getPopularMovies(page)).thenReturn(flowOf(expectedResult))

        val result = getPopularMoviesUseCase.getPopularMovies(page).first()

        assertTrue(result is AppResult.Error)
        verify(mockMovieRepository, times(1)).getPopularMovies(page)
    }
}
