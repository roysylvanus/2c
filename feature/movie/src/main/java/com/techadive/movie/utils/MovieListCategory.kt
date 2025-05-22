package com.techadive.movie.utils

import com.techadive.common.R

enum class MovieListCategory(val value: String, val titleResource: Int) {
    UPCOMING("upcoming", R.string.upcoming),
    POPULAR("popular", R.string.popular),
    NOW_PLAYING("now_playing", R.string.now_showing),
    TOP_RATED("top_rated", R.string.top_rated),
    RECOMMENDED("recommended", R.string.recommended),
    NONE("", 0)
}

fun String.getMovieCategory(): MovieListCategory =
    when(this) {
        MovieListCategory.TOP_RATED.value -> MovieListCategory.TOP_RATED
        MovieListCategory.POPULAR.value -> MovieListCategory.POPULAR
        MovieListCategory.NOW_PLAYING.value -> MovieListCategory.NOW_PLAYING
        MovieListCategory.UPCOMING.value -> MovieListCategory.UPCOMING
        MovieListCategory.RECOMMENDED.value -> MovieListCategory.RECOMMENDED
        else  -> MovieListCategory.NONE
    }