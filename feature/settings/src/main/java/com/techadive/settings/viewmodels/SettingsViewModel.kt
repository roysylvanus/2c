package com.techadive.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techadive.data.stores.settings.AppTheme
import com.techadive.settings.usecases.GetAndUpdateUserAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserAppThemeUseCase: GetAndUpdateUserAppThemeUseCase
): ViewModel() {

    private val _appTheme = MutableStateFlow(AppTheme.MODE_AUTO)
    val appTheme: StateFlow<AppTheme> get() = _appTheme

    init {
        viewModelScope.launch {
            getUserAppThemeUseCase.appThemeFlow.collect {
                _appTheme.value = it
            }
        }
    }

    fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            getUserAppThemeUseCase.updateAppTheme(appTheme)
        }
    }
}