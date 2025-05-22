package com.techadive.settings.viewmodels

import androidx.lifecycle.ViewModel
import com.techadive.data.stores.settings.AppTheme
import com.techadive.settings.usecases.GetUserAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserAppThemeUseCase: GetUserAppThemeUseCase
): ViewModel() {

    private val _appTheme = MutableStateFlow(getUserAppThemeUseCase.appTheme)
    val appTheme: StateFlow<AppTheme> get() = _appTheme

    fun updateAppTheme(appTheme: AppTheme) {
        getUserAppThemeUseCase.appTheme = appTheme
        _appTheme.value = appTheme
    }
}