package com.techadive.movie.models

import com.techadive.network.models.MovieDetailsDTO
import kotlin.String

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

fun MovieDetailsDTO.convertToMovieDetails() =
    MovieDetails(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        belongsToCollection = this.belongs_to_collection.convertToBelongsToCollection(),
        budget = this.budget,
        genres = this.genres.map { it.convertToGenre() },
        homepage = this.homepage,
        id = id,
        imdbId = this.imdb_id,
        originCountry = this.origin_country,
        originalTitle = this.original_title,
        originalLanguage = this.original_language,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.poster_path,
        productionCompanies = this.production_companies.map { it.convertToProductionCompany() },
        productionCountries = this.production_countries.map { it.convertToProductionCountry() },
        releaseDate = this.release_date,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spoken_languages.map { it.convertToSpokenLanguage() },
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count
    )
