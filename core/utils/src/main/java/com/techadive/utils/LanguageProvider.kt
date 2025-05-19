package com.techadive.utils

import java.util.Locale

/**
 * Provides the current language/locale used in API requests.
 *
 * This interface allows different implementations for language retrieval,
 * making it easier to test and adapt to user preferences or app settings in the future.
 *
 * Example usage: Used by repositories to attach the appropriate "language" query param
 * when calling TMDB API endpoints (e.g., "en-US").
 */

interface LanguageProvider {
    fun getLanguage(): String
}

class LanguageProviderImpl : LanguageProvider {
    override fun getLanguage(): String = Locale.getDefault().toLanguageTag()
}
