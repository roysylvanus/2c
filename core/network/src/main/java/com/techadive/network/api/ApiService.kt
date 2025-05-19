package com.techadive.network.api

import com.techadive.network.models.MovieListDTO
import com.techadive.network.models.MovieListWithDatesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api Endpoints to TMDB .
 ** Docs: https://developer.themoviedb.org/3/
 */
interface ApiService {

    /**
     * Endpoint to fetch upcoming movies
     * **/

    @GET("/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("page") page: Int = 1,
    ): MovieListWithDatesDTO


    /**
     * Endpoint to fetch now playing movies
     * **/

    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String,
        @Query("page") page: Int = 1,
    ): MovieListWithDatesDTO


    /**
     * Endpoint to fetch popular movies
     * **/

    @GET("/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int = 1,
    ): MovieListDTO

    /**
     * Endpoint to fetch top rated movies
     * **/

    @GET("/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int = 1,
    ): MovieListDTO


    /**
     * Endpoint to fetch movie details
     * **/

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieListDTO
}