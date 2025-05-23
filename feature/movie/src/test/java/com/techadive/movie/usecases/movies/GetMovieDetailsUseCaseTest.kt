package com.techadive.movie.usecases.movies

import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.repositories.movies.MovieRepository
import com.techadive.network.models.convertToMovieDetails
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
class GetMovieDetailsUseCaseTest {

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCaseImpl

    @Mock
    private lateinit var mockMovieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getMovieDetailsUseCase = GetMovieDetailsUseCaseImpl(mockMovieRepository)
    }

    @Test
    fun `when getMovieDetails is called with valid movieId and repository returns success, emits success result`() =
        runTest {
            val movieId = 101
            val expectedResult = AppResult.Success(FakeData.movieDetailsDTO().convertToMovieDetails())

            whenever(mockMovieRepository.getMovieDetails(movieId)).thenReturn(flowOf(expectedResult))

            val result = getMovieDetailsUseCase.getMovieDetails(movieId).first()

            assertTrue(result is AppResult.Success)
            assertEquals(expectedResult.data, (result as AppResult.Success).data)
            verify(mockMovieRepository, times(1)).getMovieDetails(movieId)
        }

    @Test
    fun `when getMovieDetails is called with valid movieId and repository returns error, emits error result`() = runTest {
        val movieId = 101
        val exception = Exception("Network error")
        val expectedResult = AppResult.Error(exception)

        whenever(mockMovieRepository.getMovieDetails(movieId)).thenReturn(flowOf(expectedResult))

        val result = getMovieDetailsUseCase.getMovieDetails(movieId).first()

        assertTrue(result is AppResult.Error)
        verify(mockMovieRepository, times(1)).getMovieDetails(movieId)
    }
}
