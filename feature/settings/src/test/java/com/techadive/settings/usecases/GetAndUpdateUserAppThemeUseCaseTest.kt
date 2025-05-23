package com.techadive.settings.usecases

import com.techadive.data.stores.settings.AppTheme
import com.techadive.data.stores.settings.UiSettingsDataStore
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import kotlin.test.assertEquals

class GetAndUpdateUserAppThemeUseCaseTest {

    private lateinit var uiSettingsDataStore: UiSettingsDataStore
    private lateinit var useCase: GetAndUpdateUserAppThemeUseCaseImpl

    private val themeFlow = MutableStateFlow(AppTheme.MODE_AUTO)

    @Before
    fun setup() {
        uiSettingsDataStore = mock()
        whenever(uiSettingsDataStore.appThemeFlow).thenReturn(themeFlow)

        useCase = GetAndUpdateUserAppThemeUseCaseImpl(uiSettingsDataStore)
    }

    @Test
    fun `appThemeFlow returns dataStore flow`() = runBlocking {
        val flow = useCase.appThemeFlow
        assertEquals(AppTheme.MODE_AUTO, flow.first())
    }

    @Test
    fun `updateAppTheme sets appTheme on dataStore`() = runBlocking {
        useCase.updateAppTheme(AppTheme.MODE_DARK)

        verify(uiSettingsDataStore).appTheme = AppTheme.MODE_DARK
    }
}