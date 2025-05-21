package com.techadive.data.di

import android.content.Context
import androidx.room.Room
import com.techadive.data.local.AppDatabase
import com.techadive.data.local.dao.MovieDao
import com.techadive.data.local.dao.SearchDao
import com.techadive.data.stores.settings.UiSettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "moves_2c.db"
        ).build()

    @Singleton
    @Provides
    fun provideUISettingsDataStore(@ApplicationContext context: Context): UiSettingsDataStore =
        UiSettingsDataStore(context)

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao =
        database.movieDao()

    @Singleton
    @Provides
    fun provideSearchDao(database: AppDatabase): SearchDao =
        database.searchDao()
}