package com.techadive.movie.repositories.search

import com.techadive.common.utils.AppResult
import com.techadive.common.providers.LanguageProvider
import com.techadive.common.models.KeywordsList
import com.techadive.common.models.MovieList
import com.techadive.data.local.dao.SearchDao
import com.techadive.data.local.entities.KeywordEntity
import com.techadive.data.local.entities.convertToKeyword
import com.techadive.network.api.ApiService
import com.techadive.network.models.convertToKeywordList
import com.techadive.network.models.convertToMovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SearchRepository {
    suspend fun searchMovies(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): Flow<AppResult<MovieList>>

    suspend fun searchKeyword(
        query: String,
        page: Int
    ): Flow<AppResult<KeywordsList>>

    suspend fun getRecentSearchHistory(): Flow<AppResult<KeywordsList>>
    suspend fun deleteAllRecentSearchHistory()
}

/**
 * Repository responsible for handling movie and keyword searches,
 * including local recent search history caching.
 */
class SearchRepositoryImpl @Inject constructor(
    private val searchDao: SearchDao,
    private val apiService: ApiService,
    private val languageProvider: LanguageProvider,
    ) : SearchRepository {

    /**
     * Searches movies remotely with given query and adult content filter,
     * updates recent search history on success.
     */
    override suspend fun searchMovies(
        query: String,
        includeAdult: Boolean,
        page: Int
    ): Flow<AppResult<MovieList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val movieList = apiService.searchMovie(
                    query = query,
                    includeAdult = includeAdult,
                    language = languageProvider.getLanguage(),
                    page = page
                )

                emit(AppResult.Success(movieList.convertToMovieList()))

                // Save or update recent search history after successful search
                addOrdUpdateRecentSearch(query)
            } catch (e: Exception) {
                emit(AppResult.Error(e))
            }
        }
    }

    /**
     * Searches keywords remotely.
     */
    override suspend fun searchKeyword(
        query: String,
        page: Int
    ): Flow<AppResult<KeywordsList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val keywordList = apiService.searchKeyword(
                    query = query,
                    page = page
                )

                emit(AppResult.Success(keywordList.convertToKeywordList()))
            } catch (e: Exception) {
                emit(AppResult.Error(e))
            }
        }
    }

    /**
     * Fetches recent search keywords from local cache.
     */
    override suspend fun getRecentSearchHistory(): Flow<AppResult<KeywordsList>> {
        return flow {
            emit(AppResult.Loading)

            try {
                val listOfKeywords = searchDao.getRecentSearch()?.map {
                    it.convertToKeyword()
                } ?: emptyList()

                emit(
                    AppResult.Success(
                        KeywordsList(
                            page = 1,
                            results = listOfKeywords,
                            totalResults = listOfKeywords.size,
                            totalPages = 1
                        )
                    )
                )

            } catch (e: Exception) {
                emit(AppResult.Error(e))
            }
        }
    }

    /**
     * Deletes all recent search entries from local cache.
     */
    override suspend fun deleteAllRecentSearchHistory() {
        searchDao.deleteAllRecentSearchHistory()
    }

    /**
     * Inserts or updates a recent search entry with the current timestamp.
     */
    private suspend fun addOrdUpdateRecentSearch(query: String) {
        searchDao.addOrUpdateRecentSearch(
            KeywordEntity(
                keyword = query,
                timeStamp = System.currentTimeMillis()
            )
        )
    }
}