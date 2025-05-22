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

class SearchRepositoryImpl @Inject constructor(
    private val searchDao: SearchDao,
    private val apiService: ApiService,
    private val languageProvider: LanguageProvider,

    ) : SearchRepository {
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

                addOrdUpdateRecentSearch(query)
            } catch (e: Exception) {
                emit(AppResult.Error(e))
            }
        }
    }

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

    override suspend fun deleteAllRecentSearchHistory() {
        searchDao.deleteAllRecentSearchHistory()
    }

    private suspend fun addOrdUpdateRecentSearch(query: String) {
        searchDao.addOrUpdateRecentSearch(
            KeywordEntity(
                keyword = query,
                timeStamp = System.currentTimeMillis()
            )
        )
    }
}