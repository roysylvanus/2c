package com.techadive.settings.usecases

import com.techadive.data.stores.settings.AppTheme
import com.techadive.data.stores.settings.UiSettingsDataStore
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

class GetAndUpdateUserAppThemeUseCaseImplTest {

    @Mock
    private lateinit var mockUiSettingsDataStore: UiSettingsDataStore
    private lateinit var useCase: GetAndUpdateUserAppThemeUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetAndUpdateUserAppThemeUseCaseImpl(mockUiSettingsDataStore)
    }

    @Test
    fun `appTheme getter should return value from UiSettingsDataStore`() {
        whenever(mockUiSettingsDataStore.appTheme).thenReturn(AppTheme.MODE_DARK)

        val result = useCase.appTheme

        assert(result == AppTheme.MODE_DARK)
        verify(mockUiSettingsDataStore).appTheme
    }

    @Test
    fun `appTheme setter should update value in UiSettingsDataStore`() {
        useCase.appTheme = AppTheme.MODE_LIGHT

        verify(mockUiSettingsDataStore).appTheme = AppTheme.MODE_LIGHT
    }
}
