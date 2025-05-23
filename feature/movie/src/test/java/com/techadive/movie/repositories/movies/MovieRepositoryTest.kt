package com.techadive.movie.repositories.movies

import app.cash.turbine.test
import com.techadive.common.providers.LanguageProvider
import com.techadive.common.utils.AppResult
import com.techadive.data.local.dao.MovieDao
import com.techadive.data.local.entities.convertToMovieListEntity
import com.techadive.movie.FakeData
import com.techadive.movie.utils.MovieListCategory
import com.techadive.network.api.ApiService
import com.techadive.network.models.convertToMovieList
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private lateinit var movieRepository: MovieRepositoryImpl

    @Mock
    private lateinit var mockApiService: ApiService
    @Mock
    private lateinit var mockLanguageProvider: LanguageProvider
    @Mock
    private lateinit var mockMovieDao: MovieDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieRepository = MovieRepositoryImpl(mockApiService, mockLanguageProvider, mockMovieDao)
        whenever(mockLanguageProvider.getLanguage()).thenReturn("en-US")
    }

    // -- NOW PLAYING --

    @Test
    fun `when getNowPlayingMovies is called and is successful, then loading and correct movie list is emitted`() =
        runTest {
            val dto = FakeData.movieListWithDatesDTO()
            whenever(mockApiService.getNowPlayingMovies("en-US", 1)).thenReturn(dto)

            movieRepository.getNowPlayingMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                val result = awaitItem()
                TestCase.assertTrue(result is AppResult.Success)
                assertEquals(1, (result as AppResult.Success).data.page)
                awaitComplete()
            }
        }

    @Test
    fun `when getNowPlayingMovies is called and api throws exception but cache exists, emits cached data and error`() =
        runTest {
            val exception = RuntimeException("Network Error")
            val cachedEntity =
                FakeData.movieListWithDatesDTO().convertToMovieList().convertToMovieListEntity()

            whenever(mockApiService.getNowPlayingMovies("en-US", 1)).thenThrow(exception)
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.NOW_PLAYING.value)).thenReturn(
                cachedEntity
            )

            movieRepository.getNowPlayingMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    @Test
    fun `when getNowPlayingMovies is called and api throws exception and no cache, emits error only`() =
        runTest {
            whenever(
                mockApiService.getNowPlayingMovies(
                    "en-US",
                    1
                )
            ).thenThrow(RuntimeException("Error"))
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.NOW_PLAYING.value)).thenReturn(
                null
            )

            movieRepository.getNowPlayingMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    // -- POPULAR --

    @Test
    fun `when getPopularMovies is called and api returns data, emits loading and success`() =
        runTest {
            val dto = FakeData.movieListDTO()
            whenever(mockApiService.getPopularMovies("en-US", 1)).thenReturn(dto)

            movieRepository.getPopularMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                awaitComplete()
            }
        }

    @Test
    fun `when getPopularMovies is called and api throws exception but cache exists, emits cached data and error`() =
        runTest {
            val exception = RuntimeException("Network Error")
            val cachedEntity =
                FakeData.movieListDTO().convertToMovieList().convertToMovieListEntity()

            whenever(mockApiService.getPopularMovies("en-US", 1)).thenThrow(exception)
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.POPULAR.value)).thenReturn(
                cachedEntity
            )

            movieRepository.getPopularMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    @Test
    fun `when getPopularMovies is called and api throws exception and no cache, emits error only`() =
        runTest {
            whenever(
                mockApiService.getPopularMovies(
                    "en-US",
                    1
                )
            ).thenThrow(RuntimeException("Error"))
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.POPULAR.value)).thenReturn(
                null
            )

            movieRepository.getPopularMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    // -- TOP RATED --

    @Test
    fun `when getTopRatedMovies is called and api returns data, emits loading and success`() =
        runTest {
            val dto = FakeData.movieListDTO()
            whenever(mockApiService.getTopRatedMovies("en-US", 1)).thenReturn(dto)

            movieRepository.getTopRatedMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                awaitComplete()
            }
        }

    @Test
    fun `when getTopRatedMovies is called and api throws exception but cache exists, emits cached data and error`() =
        runTest {
            val exception = RuntimeException("Network Error")
            val cachedEntity =
                FakeData.movieListDTO().convertToMovieList().convertToMovieListEntity()

            whenever(mockApiService.getTopRatedMovies("en-US", 1)).thenThrow(exception)
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.TOP_RATED.value)).thenReturn(
                cachedEntity
            )

            movieRepository.getTopRatedMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    @Test
    fun `when getTopRatedMovies is called and api throws exception and no cache, emits error only`() =
        runTest {
            whenever(
                mockApiService.getTopRatedMovies(
                    "en-US",
                    1
                )
            ).thenThrow(RuntimeException("Error"))
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.TOP_RATED.value)).thenReturn(
                null
            )

            movieRepository.getTopRatedMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    // -- UPCOMING --

    @Test
    fun `when getUpcomingMovies is called and api returns data, emits loading and success`() =
        runTest {
            val dto = FakeData.movieListWithDatesDTO()
            whenever(mockApiService.getUpcomingMovies("en-US", 1)).thenReturn(dto)

            movieRepository.getUpcomingMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                awaitComplete()
            }
        }

    @Test
    fun `when getUpcomingMovies is called and api throws exception but cache exists, emits cached data and error`() =
        runTest {
            val exception = RuntimeException("Network Error")
            val cachedEntity =
                FakeData.movieListDTO().convertToMovieList().convertToMovieListEntity()

            whenever(mockApiService.getUpcomingMovies("en-US", 1)).thenThrow(exception)
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.UPCOMING.value)).thenReturn(
                cachedEntity
            )

            movieRepository.getUpcomingMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Success)
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    @Test
    fun `when getUpcomingMovies is called and api throws exception and no cache, emits error only`() =
        runTest {
            whenever(
                mockApiService.getUpcomingMovies(
                    "en-US",
                    1
                )
            ).thenThrow(RuntimeException("Error"))
            whenever(mockMovieDao.getMovieListByCategory(MovieListCategory.UPCOMING.value)).thenReturn(
                null
            )

            movieRepository.getUpcomingMovies(1).test {
                assertEquals(AppResult.Loading, awaitItem())
                TestCase.assertTrue(awaitItem() is AppResult.Error)
                awaitComplete()
            }
        }

    // -- MOVIE DETAILS --

    @Test
    fun `getMovieDetails success`() = runTest {
        val dto = FakeData.movieDetailsDTO()
        whenever(mockApiService.getMovieDetails(10, "en-US")).thenReturn(dto)

        movieRepository.getMovieDetails(10).test {
            assertEquals(AppResult.Loading, awaitItem())
            TestCase.assertTrue(awaitItem() is AppResult.Success)
            awaitComplete()
        }
    }

    @Test
    fun `getMovieDetails error`() = runTest {
        whenever(mockApiService.getMovieDetails(10, "en-US")).thenThrow(RuntimeException("Error"))

        movieRepository.getMovieDetails(10).test {
            assertEquals(AppResult.Loading, awaitItem())
            TestCase.assertTrue(awaitItem() is AppResult.Error)
            awaitComplete()
        }
    }

    // -- RECOMMENDED --

    @Test
    fun `getRecommendedMovies success`() = runTest {
        val dto = FakeData.movieListDTO()
        whenever(mockApiService.getRecommendedMovies(12, "en-US", 1)).thenReturn(dto)

        movieRepository.getRecommendedMovies(12, 1).test {
            assertEquals(AppResult.Loading, awaitItem())
            TestCase.assertTrue(awaitItem() is AppResult.Success)
            awaitComplete()
        }
    }

    @Test
    fun `getRecommendedMovies error`() = runTest {
        whenever(
            mockApiService.getRecommendedMovies(
                12,
                "en-US",
                1
            )
        ).thenThrow(RuntimeException("Fail"))

        movieRepository.getRecommendedMovies(12, 1).test {
            assertEquals(AppResult.Loading, awaitItem())
            TestCase.assertTrue(awaitItem() is AppResult.Error)
            awaitComplete()
        }
    }
}