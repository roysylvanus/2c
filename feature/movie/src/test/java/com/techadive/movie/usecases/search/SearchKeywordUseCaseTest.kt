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
class SearchKeywordUseCaseTest {

    @Mock
    private lateinit var searchRepository: SearchRepository

    private lateinit var useCase: SearchKeywordUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SearchKeywordUseCaseImpl(searchRepository)
    }

    @Test
    fun `whenever searchKeyword is called and repository returns success, emits success`() = runTest {
        // Arrange
        val query = "test"
        val page = 1
        val expectedResult = AppResult.Success(
            KeywordsList(
                page = page,
                results = listOf(Keyword(id = 1, name = "TestKeyword")),
                totalResults = 1,
                totalPages = 1
            )
        )

        whenever(searchRepository.searchKeyword(query, page)).thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.searchKeyword(query, page).test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `whenever searchKeyword is called and repository returns error, emits error`() = runTest {
        // Arrange
        val query = "test"
        val page = 1
        val exception = Exception("Network error")
        val expectedResult = AppResult.Error(exception)

        whenever(searchRepository.searchKeyword(query, page)).thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.searchKeyword(query, page).test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `whenever searchKeyword is called and repository emits loading, emits loading`() = runTest {
        // Arrange
        val query = "test"
        val page = 1
        val expectedResult = AppResult.Loading

        whenever(searchRepository.searchKeyword(query, page)).thenReturn(flowOf(expectedResult))

        // Act & Assert
        useCase.searchKeyword(query, page).test {
            assertEquals(expectedResult, awaitItem())
            awaitComplete()
        }
    }
}
