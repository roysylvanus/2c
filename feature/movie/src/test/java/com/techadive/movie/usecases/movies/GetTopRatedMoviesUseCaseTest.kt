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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetTopRatedMoviesUseCaseTest {

    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCaseImpl

    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTopRatedMoviesUseCase = GetTopRatedMoviesUseCaseImpl(mockMovieRepository)
    }

    @Test
    fun `when getTopRatedMovies is called and repository returns success, emits success result`() = runTest {
        val page = 1
        val expectedResult = AppResult.Success(FakeData.movieListDTO().convertToMovieList())

        whenever(mockMovieRepository.getTopRatedMovies(page)).thenReturn(flowOf(expectedResult))

        val result = getTopRatedMoviesUseCase.getTopRatedMovies(page).first()

        assertTrue(result is AppResult.Success)
        assertEquals(expectedResult.data, (result as AppResult.Success).data)
        verify(mockMovieRepository).getTopRatedMovies(page)
    }

    @Test
    fun `when getTopRatedMovies is called and repository returns error, emits error result`() = runTest {
        val page = 1
        val exception = Exception("Something went wrong")
        val expectedResult = AppResult.Error(exception)

        whenever(mockMovieRepository.getTopRatedMovies(page)).thenReturn(flowOf(expectedResult))

        val result = getTopRatedMoviesUseCase.getTopRatedMovies(page).first()

        assertTrue(result is AppResult.Error)
        verify(mockMovieRepository).getTopRatedMovies(page)
    }
}
