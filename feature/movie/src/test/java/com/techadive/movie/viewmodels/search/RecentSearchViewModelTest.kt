package com.techadive.movie.viewmodels.search

import app.cash.turbine.test
import com.techadive.common.models.KeywordsList
import com.techadive.common.utils.AppResult
import com.techadive.movie.usecases.search.DeleteAllRecentSearchHistoryUseCase
import com.techadive.movie.usecases.search.GetRecentSearchHistoryUseCase
import com.techadive.movie.usecases.search.SearchKeywordUseCase
import kotlinx.coroutines.*
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
class RecentSearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var searchKeywordUseCase: SearchKeywordUseCase
    private lateinit var getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
    private lateinit var deleteAllRecentSearchHistoryUseCase: DeleteAllRecentSearchHistoryUseCase

    private lateinit var viewModel: RecentSearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        searchKeywordUseCase = mock()
        getRecentSearchHistoryUseCase = mock()
        deleteAllRecentSearchHistoryUseCase = mock()

        viewModel = RecentSearchViewModel(
            searchKeywordUseCase,
            getRecentSearchHistoryUseCase,
            deleteAllRecentSearchHistoryUseCase
        )
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
    fun `searchKeyword updates UIState correctly on success`() = runTest {
        val keywords = KeywordsList( results = emptyList(),
            page = 1,
            totalPages = 1,
            totalResults = 0
        )

        whenever(searchKeywordUseCase.searchKeyword(any(), any())).thenReturn(flowOfLoadingAndSuccess(keywords))

        viewModel.onEvent(RecentSearchViewModel.RecentSearchEvent.SearchKeyword("query"))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.recentSearchUIState.value
        assertEquals("query", state.query)
        assertFalse(state.isLoading)
        assertFalse(state.isError)
        assertEquals(keywords, state.keywordsList)
    }

    @Test
    fun `searchKeyword updates UIState correctly on error`() = runTest {
        whenever(searchKeywordUseCase.searchKeyword(any(), any())).thenReturn(flowOfError())

        viewModel.onEvent(RecentSearchViewModel.RecentSearchEvent.SearchKeyword("query"))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.recentSearchUIState.value
        assertEquals("query", state.query)
        assertFalse(state.isLoading)
        assertTrue(state.isError)
        assertEquals(null, state.keywordsList)
    }

    @Test
    fun `getRecentSearchHistory updates UIState correctly on success`() = runTest {
        val recentKeywords = KeywordsList( results = emptyList(),
            page = 1,
            totalPages = 1,
            totalResults = 0
        )

        whenever(getRecentSearchHistoryUseCase.getRecentSearchHistory()).thenReturn(flowOfLoadingAndSuccess(recentKeywords))

        viewModel.onEvent(RecentSearchViewModel.RecentSearchEvent.GetRecentSearchHistory)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.recentSearchUIState.value
        assertEquals(null, state.query)
        assertFalse(state.isLoading)
        assertFalse(state.isError)
        assertEquals(recentKeywords, state.recentKeywords)
    }

    @Test
    fun `getRecentSearchHistory updates UIState correctly on error`() = runTest {
        whenever(getRecentSearchHistoryUseCase.getRecentSearchHistory()).thenReturn(flowOfError())

        viewModel.onEvent(RecentSearchViewModel.RecentSearchEvent.GetRecentSearchHistory)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.recentSearchUIState.value
        assertEquals(null, state.query)
        assertFalse(state.isLoading)
        assertTrue(state.isError)
        assertEquals(null, state.recentKeywords)
    }

    @Test
    fun `deleteAllRecentSearchHistory calls useCase and refreshes history`() = runTest {
        whenever(getRecentSearchHistoryUseCase.getRecentSearchHistory()).thenReturn(flowOfLoadingAndSuccess(KeywordsList( results = emptyList(),
            page = 1,
            totalPages = 1,
            totalResults = 0
        )))

        viewModel.onEvent(RecentSearchViewModel.RecentSearchEvent.DeleteAllRecentSearchHistory)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(deleteAllRecentSearchHistoryUseCase).deleteAllRecentSearchHistory()
        verify(getRecentSearchHistoryUseCase).getRecentSearchHistory()
    }
}
