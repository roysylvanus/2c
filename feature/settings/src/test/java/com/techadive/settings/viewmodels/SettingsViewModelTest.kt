package com.techadive.settings.viewmodels

import app.cash.turbine.test
import com.techadive.data.stores.settings.AppTheme
import com.techadive.settings.usecases.GetAndUpdateUserAppThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    @Mock
    private lateinit var mockGetUserAppThemeUseCase: GetAndUpdateUserAppThemeUseCase
    private lateinit var viewModel: SettingsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        whenever(mockGetUserAppThemeUseCase.appTheme).thenReturn(AppTheme.MODE_LIGHT)
        viewModel = SettingsViewModel(mockGetUserAppThemeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial app theme should be LIGHT`() = runTest {
        viewModel.appTheme.test {
            assert(awaitItem() == AppTheme.MODE_LIGHT)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When updateAppTheme is called, then should change app theme and call use case`() = runTest {
        val newTheme = AppTheme.MODE_DARK

        viewModel.updateAppTheme(newTheme)

        viewModel.appTheme.test {
            assert(awaitItem() == newTheme)
            cancelAndIgnoreRemainingEvents()
        }

        verify(mockGetUserAppThemeUseCase).appTheme = newTheme
    }
}