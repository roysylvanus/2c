package com.techadive.movie

import com.techadive.network.models.BelongsToCollectionDTO
import com.techadive.network.models.DatesDTO
import com.techadive.network.models.GenreDTO
import com.techadive.network.models.MovieDTO
import com.techadive.network.models.MovieDetailsDTO
import com.techadive.network.models.MovieListDTO
import com.techadive.network.models.MovieListWithDatesDTO
import com.techadive.network.models.ProductionCompanyDTO
import com.techadive.network.models.ProductionCountryDTO
import com.techadive.network.models.SpokenLanguageDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi

object FakeData {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun movieListWithDatesDTO(): MovieListWithDatesDTO {
        return MovieListWithDatesDTO(
            dates = DatesDTO(
                maximum = "2025-01-02",
                minimum = "2025-01-01"
            ),
            page = 1,
            results = listOf(
                MovieDTO(
                    id = 101,
                    title = "Fake Movie",
                    overview = "A test movie for unit testing.",
                    release_date = "2025-01-01",
                    poster_path = "/poster.jpg",
                    backdrop_path = "/backdrop.jpg",
                    vote_average = 7.5,
                    vote_count = 1500,
                    adult = false,
                    genre_ids = listOf(28, 12),
                    original_language = "en",
                    original_title = "Fake Movie",
                    popularity = 300.0,
                    video = false
                )
            ),
            total_pages = 1,
            total_results = 1
        )
    }

    fun movieDetailsDTO(): MovieDetailsDTO {
        return MovieDetailsDTO(
            adult = false,
            backdrop_path = "/backdrop.jpg",
            belongs_to_collection = BelongsToCollectionDTO(
                id = 123,
                name = "Fake Collection",
                poster_path = "/collection_poster.jpg",
                backdrop_path = "/collection_backdrop.jpg"
            ),
            budget = 100000000,
            genres = listOf(
                GenreDTO(id = 28, name = "Action"),
                GenreDTO(id = 12, name = "Adventure")
            ),
            homepage = "https://fakemovie.example.com",
            id = 101,
            imdb_id = "tt1234567",
            origin_country = listOf("US"),
            original_language = "en",
            original_title = "Fake Movie Original Title",
            overview = "This is a fake movie overview for testing purposes.",
            popularity = 350.5,
            poster_path = "/poster.jpg",
            production_companies = listOf(
                ProductionCompanyDTO(
                    id = 1,
                    logo_path = "/logo.png",
                    name = "Fake Production Co",
                    origin_country = "US"
                )
            ),
            production_countries = listOf(
                ProductionCountryDTO(iso_3166_1 = "US", name = "United States of America")
            ),
            release_date = "2025-01-01",
            revenue = 500000000,
            runtime = 130,
            spoken_languages = listOf(
                SpokenLanguageDTO(iso_639_1 = "en", name = "English", english_name = "")
            ),
            status = "Released",
            tagline = "This is the fake movie tagline.",
            title = "Fake Movie",
            video = false,
            vote_average = 7.8,
            vote_count = 1200
        )
    }


    fun movieListDTO(): MovieListDTO {
        return MovieListDTO(
            page = 1,
            results = listOf(
                MovieDTO(
                    id = 101,
                    title = "Fake Movie",
                    overview = "A test movie for unit testing.",
                    release_date = "2025-01-01",
                    poster_path = "/poster.jpg",
                    backdrop_path = "/backdrop.jpg",
                    vote_average = 7.5,
                    vote_count = 1500,
                    adult = false,
                    genre_ids = listOf(28, 12),
                    original_language = "en",
                    original_title = "Fake Movie",
                    popularity = 300.0,
                    video = false
                )
            ),
            total_pages = 1,
            total_results = 1
        )
    }
}