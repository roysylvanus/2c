package com.techadive.common

sealed class AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Error(val error: Exception) : AppResult<Nothing>()
    data object Loading : AppResult<Nothing>()
}