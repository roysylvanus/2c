package com.techadive.settings.usecases

import com.techadive.data.stores.settings.AppTheme
import com.techadive.data.stores.settings.UiSettingsDataStore
import javax.inject.Inject

interface GetUserAppThemeUseCase {
    var appTheme: AppTheme
}

class GetUserAppThemeUseCaseImpl @Inject constructor(
    private val uiSettingsDataStore: UiSettingsDataStore
) : GetUserAppThemeUseCase {

    override var appTheme: AppTheme
        get() = uiSettingsDataStore.appTheme
        set(value) {
            uiSettingsDataStore.appTheme = value
        }
}