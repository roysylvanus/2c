package com.techadive.movie.usecases.search

import com.techadive.movie.repositories.search.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteAllRecentSearchHistoryUseCaseTest {

    private lateinit var useCase: DeleteAllRecentSearchHistoryUseCaseImpl

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = DeleteAllRecentSearchHistoryUseCaseImpl(searchRepository)
    }

    @Test
    fun `when deleteAllRecentSearchHistory is called, repository method is invoked`() = runTest {
        // Act
        useCase.deleteAllRecentSearchHistory()

        // Assert
        verify(searchRepository).deleteAllRecentSearchHistory()
    }
}
