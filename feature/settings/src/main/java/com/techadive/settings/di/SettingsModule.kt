package com.techadive.settings.di

import com.techadive.data.stores.settings.UiSettingsDataStore
import com.techadive.settings.usecases.GetAndUpdateUserAppThemeUseCase
import com.techadive.settings.usecases.GetAndUpdateUserAppThemeUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {
    @Provides
    @Singleton
    fun provideGetAndUpdateUserAppThemeUseCase(uiSettingsDataStore: UiSettingsDataStore): GetAndUpdateUserAppThemeUseCase =
        GetAndUpdateUserAppThemeUseCaseImpl(uiSettingsDataStore)
}