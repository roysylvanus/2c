package com.techadive.movie.usecases.search

import app.cash.turbine.test
import com.techadive.common.models.Keyword
import com.techadive.common.models.KeywordsList
import com.techadive.common.utils.AppResult
import com.techadive.movie.repositories.search.SearchRepository
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
class GetRecentSearchHistoryUseCaseTest {

    @Mock
    private lateinit var searchRepository: SearchRepository

    private lateinit var useCase: GetRecentSearchHistoryUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetRecentSearchHistoryUseCaseImpl(searchRepository)
    }

    @Test
    fun `when getRecentSearchHistory is called and repository returns success, emits success`() = runTest {
        // Arrange
        val expectedResult = AppResult.Success(
            KeywordsList(
                page = 1,
                results = listOf(Keyword(id = 1, name = "Test")),
                totalResults = 1,
                totalPages = 1
            )
        )

        whenever(searchRepository.getRecentSearchHistory()).thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.getRecentSearchHistory().test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when getRecentSearchHistory is called and repository returns error, emits error`() = runTest {
        // Arrange
        val exception = Exception("Network error")
        val expectedResult = AppResult.Error(exception)

        whenever(searchRepository.getRecentSearchHistory()).thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.getRecentSearchHistory().test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when getRecentSearchHistory is called and repository emits loading, emits loading`() = runTest {
        // Arrange
        val expectedResult = AppResult.Loading

        whenever(searchRepository.getRecentSearchHistory()).thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.getRecentSearchHistory().test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }
}