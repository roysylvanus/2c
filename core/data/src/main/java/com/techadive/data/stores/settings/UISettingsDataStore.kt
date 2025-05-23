package com.techadive.data.stores.settings

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UiSettingsDataStore @Inject constructor(
    context: Context,
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)

    private val _appThemeFlow = MutableStateFlow(loadAppThemeFromPrefs())

    val appThemeFlow: StateFlow<AppTheme> = _appThemeFlow.asStateFlow()

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == UI_MODE) {
            _appThemeFlow.value = loadAppThemeFromPrefs()
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    var appTheme: AppTheme
        get() = _appThemeFlow.value
        set(value) {
            sharedPreferences.edit().putString(UI_MODE, value.title).apply()
            // _appThemeFlow.value will be updated by listener automatically
        }

    private fun loadAppThemeFromPrefs(): AppTheme {
        return when (sharedPreferences.getString(UI_MODE, AppTheme.MODE_AUTO.title)) {
            AppTheme.MODE_DARK.title -> AppTheme.MODE_DARK
            AppTheme.MODE_LIGHT.title -> AppTheme.MODE_LIGHT
            else -> AppTheme.MODE_AUTO
        }
    }

    private companion object {
        const val USER_SETTINGS = "user_settings"
        const val UI_MODE = "ui_mode"
    }
}