package com.techadive.settings.usecases

import com.techadive.data.stores.settings.AppTheme
import com.techadive.data.stores.settings.UiSettingsDataStore
import javax.inject.Inject

interface GetAndUpdateUserAppThemeUseCase {
    var appTheme: AppTheme
}

class GetAndUpdateUserAppThemeUseCaseImpl @Inject constructor(
    private val uiSettingsDataStore: UiSettingsDataStore
) : GetAndUpdateUserAppThemeUseCase {

    override var appTheme: AppTheme
        get() = uiSettingsDataStore.appTheme
        set(value) {
            uiSettingsDataStore.appTheme = value
        }
}