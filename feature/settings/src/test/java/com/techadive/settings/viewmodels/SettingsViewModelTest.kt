package com.techadive.settings.viewmodels

import app.cash.turbine.test
import com.techadive.data.stores.settings.AppTheme
import com.techadive.settings.usecases.GetAndUpdateUserAppThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.assertEquals

class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getUserAppThemeUseCase: GetAndUpdateUserAppThemeUseCase
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getUserAppThemeUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init collects appThemeFlow and updates _appTheme`() = runTest {
        // Given a MutableStateFlow emitting AppTheme.MODE_LIGHT
        val flow = MutableStateFlow(AppTheme.MODE_LIGHT)
        whenever(getUserAppThemeUseCase.appThemeFlow).thenReturn(flow)

        // When
        viewModel = SettingsViewModel(getUserAppThemeUseCase)

        // Then: collect from viewModel.appTheme and verify initial value is MODE_LIGHT
        viewModel.appTheme.test {
            assertEquals(AppTheme.MODE_LIGHT, awaitItem())
        }
    }

    @Test
    fun `updateAppTheme calls useCase updateAppTheme`() = runTest {
        // Given
        whenever(getUserAppThemeUseCase.appThemeFlow).thenReturn(MutableStateFlow(AppTheme.MODE_AUTO))
        viewModel = SettingsViewModel(getUserAppThemeUseCase)

        // When
        viewModel.updateAppTheme(AppTheme.MODE_DARK)

        // Advance dispatcher so coroutine executes
        advanceUntilIdle()

        // Then verify updateAppTheme called once with MODE_DARK
        verify(getUserAppThemeUseCase).updateAppTheme(AppTheme.MODE_DARK)
    }
}