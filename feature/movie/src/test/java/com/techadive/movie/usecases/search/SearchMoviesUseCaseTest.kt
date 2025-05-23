package com.techadive.movie.usecases.search

import app.cash.turbine.test
import com.techadive.common.models.MovieCardData
import com.techadive.common.models.MovieList
import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.movie.repositories.search.SearchRepository
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMoviesUseCaseTest {

    @Mock
    private lateinit var searchRepository: SearchRepository

    private lateinit var useCase: SearchMoviesUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SearchMoviesUseCaseImpl(searchRepository)
    }

    @Test
    fun `whenever searchMovies is called and repository returns success, emits success`() = runTest {
        // Arrange
        val query = "test"
        val includeAdult = false
        val page = 1
        val expectedResult = AppResult.Success(
            FakeData.movieListDTO().convertToMovieList()
        )

        whenever(searchRepository.searchMovies(query, includeAdult, page))
            .thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.searchMovies(query, includeAdult, page).test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `whenever searchMovies is called and repository returns error, emits error`() = runTest {
        // Arrange
        val query = "test"
        val includeAdult = true
        val page = 2
        val exception = Exception("Network error")
        val expectedResult = AppResult.Error(exception)

        whenever(searchRepository.searchMovies(query, includeAdult, page))
            .thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.searchMovies(query, includeAdult, page).test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `whenever searchMovies is called and repository emits loading, emits loading`() = runTest {
        // Arrange
        val query = "test"
        val includeAdult = false
        val page = 1
        val expectedResult = AppResult.Loading

        whenever(searchRepository.searchMovies(query, includeAdult, page))
            .thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.searchMovies(query, includeAdult, page).test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }
}
