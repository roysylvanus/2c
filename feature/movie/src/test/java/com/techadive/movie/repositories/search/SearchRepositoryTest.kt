package com.techadive.movie.repositories.search

import app.cash.turbine.test
import com.techadive.data.local.dao.SearchDao
import com.techadive.data.local.entities.KeywordEntity
import com.techadive.network.api.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import com.techadive.common.models.KeywordsList
import com.techadive.common.models.MovieList
import com.techadive.common.providers.LanguageProvider
import com.techadive.common.utils.AppResult
import com.techadive.movie.FakeData
import com.techadive.network.models.KeywordDto
import com.techadive.network.models.KeywordsListDto
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SearchRepositoryTest {

    private lateinit var searchRepository: SearchRepositoryImpl

    @Mock
    private lateinit var mockSearchDao: SearchDao

    @Mock
    private lateinit var mockApiService: ApiService

    @Mock
    private lateinit var mockLanguageProvider: LanguageProvider

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        searchRepository = SearchRepositoryImpl(
            mockSearchDao,
            mockApiService,
            mockLanguageProvider
        )

        whenever(mockLanguageProvider.getLanguage()).thenReturn("en-US")
    }

    @Test
    fun `when searchMovies is called and api returns data, emits loading and success and updates recent search`() = runTest {
        val query = "batman"
        val includeAdult = false
        val page = 1

        whenever(mockApiService.searchMovie(query, includeAdult, "en-US", page))
            .thenReturn(FakeData.movieListDTO())

        searchRepository.searchMovies(query, includeAdult, page).test {
            assertEquals(AppResult.Loading, awaitItem())

            val success = awaitItem()
            assertTrue(success is AppResult.Success)

            verify(mockApiService, times(1)).searchMovie(query, includeAdult, "en-US", page)
            verify(mockSearchDao, times(1)).addOrUpdateRecentSearch(any())

            awaitComplete()
        }
    }

    @Test
    fun `when searchKeyword is called and api returns data, emits loading and success`() = runTest {
        val query = "action"
        val page = 1

        val keywordListDTO = KeywordsListDto(
            page = 1,
            results = listOf(KeywordDto(id = 1, name = "Action")),
            total_results = 1,
            total_pages = 1
        )

        whenever(mockApiService.searchKeyword(query, page)).thenReturn(keywordListDTO)

        searchRepository.searchKeyword(query, page).test {
            assertEquals(AppResult.Loading, awaitItem())

            val success = awaitItem()
            assertTrue(success is AppResult.Success)

            verify(mockApiService, times(1)).searchKeyword(query, page)

            awaitComplete()
        }
    }

    @Test
    fun `when getRecentSearchHistory is called and dao returns data, emits loading and success`() = runTest {
        val keywordEntities = listOf(
            KeywordEntity(keyword = "Action", timeStamp = 123456789L)
        )

        whenever(mockSearchDao.getRecentSearch()).thenReturn(keywordEntities)

        searchRepository.getRecentSearchHistory().test {
            assertEquals(AppResult.Loading, awaitItem())

            val success = awaitItem()
            assertTrue(success is AppResult.Success)

            val data = (success as AppResult.Success).data
            assertEquals(keywordEntities.size, data.results.size)

            verify(mockSearchDao, times(1)).getRecentSearch()

            awaitComplete()
        }
    }

    @Test
    fun `when deleteAllRecentSearchHistory is called, dao delete method is called`() = runTest {
        searchRepository.deleteAllRecentSearchHistory()

        verify(mockSearchDao, times(1)).deleteAllRecentSearchHistory()
    }
}
