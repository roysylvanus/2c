package com.techadive.settings.usecases

import com.techadive.data.stores.settings.AppTheme
import com.techadive.data.stores.settings.UiSettingsDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAndUpdateUserAppThemeUseCase {
    val appThemeFlow: Flow<AppTheme>
    suspend fun updateAppTheme(appTheme: AppTheme)
}

class GetAndUpdateUserAppThemeUseCaseImpl @Inject constructor(
    private val uiSettingsDataStore: UiSettingsDataStore
) : GetAndUpdateUserAppThemeUseCase {

    override val appThemeFlow: Flow<AppTheme> = uiSettingsDataStore.appThemeFlow

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        uiSettingsDataStore.appTheme = appTheme
    }
}