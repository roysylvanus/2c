package com.techadive.settings.di

import com.techadive.data.stores.settings.UiSettingsDataStore
import com.techadive.settings.usecases.GetUserAppThemeUseCase
import com.techadive.settings.usecases.GetUserAppThemeUseCaseImpl
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
    fun provideGetUserAppThemeUseCase(uiSettingsDataStore: UiSettingsDataStore): GetUserAppThemeUseCase =
        GetUserAppThemeUseCaseImpl(uiSettingsDataStore)
}