package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.repositories.movies.MovieRepository
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetUpcomingMoviesUseCaseImplTest {

    private lateinit var useCase: GetUpcomingMoviesUseCaseImpl

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetUpcomingMoviesUseCaseImpl(movieRepository)
    }

    @Test
    fun `when getUpcomingMovies is called and repository returns success, emits success result`() = runTest {
        val page = 1
        val expected = AppResult.Success(FakeData.movieListDTO().convertToMovieList())

        whenever(movieRepository.getUpcomingMovies(page)).thenReturn(flowOf(expected))

        val result = useCase.getUpcomingMovies(page).first()

        assertTrue(result is AppResult.Success)
        assertEquals(expected.data, (result as AppResult.Success).data)
        verify(movieRepository).getUpcomingMovies(page)
    }

    @Test
    fun `when getUpcomingMovies is called and repository returns error, emits error result`() = runTest {
        val page = 1
        val exception = Exception("Network error")
        val expected = AppResult.Error(exception)

        whenever(movieRepository.getUpcomingMovies(page)).thenReturn(flowOf(expected))

        val result = useCase.getUpcomingMovies(page).first()

        assertTrue(result is AppResult.Error)
        verify(movieRepository).getUpcomingMovies(page)
    }
}
