package com.techadive.data.stores.settings

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class UiSettingsDataStore @Inject constructor(
    context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)

    var appTheme: AppTheme = when(sharedPreferences.getString(UI_MODE, AppTheme.MODE_AUTO.title)) {
        AppTheme.MODE_DARK.title -> AppTheme.MODE_DARK
        AppTheme.MODE_LIGHT.title -> AppTheme.MODE_LIGHT
        else -> AppTheme.MODE_AUTO
    }

        set(uiMode) {
            sharedPreferences.edit().putString(UI_MODE, uiMode.title).apply()
            field = uiMode
        }

    private companion object {
        const val USER_SETTINGS = "user_settings"
        const val UI_MODE = "ui_mode"
    }
}