package com.techadive.network.models

import com.techadive.common.models.MovieDetails

data class MovieDetailsDTO(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: BelongsToCollectionDTO?,
    val budget: Int,
    val genres: List<GenreDTO>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanyDTO>?,
    val production_countries: List<ProductionCountryDTO>?,
    val release_date: String?,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguageDTO>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailsDTO.convertToMovieDetails() =
    MovieDetails(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        belongsToCollection = this.belongs_to_collection?.convertToBelongsToCollection(),
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
        productionCompanies = this.production_companies?.map { it.convertToProductionCompany() },
        productionCountries = this.production_countries?.map { it.convertToProductionCountry() },
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
