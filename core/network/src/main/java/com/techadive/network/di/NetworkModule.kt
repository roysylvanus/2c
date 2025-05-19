package com.techadive.network.di

import com.techadive.network.api.ApiManager
import com.techadive.network.api.ApiService
import com.techadive.network.retrofit.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): RetrofitInstance =
        RetrofitInstance()

    @Singleton
    @Provides
    fun provideApiService(retrofitInstance: RetrofitInstance): ApiService =
        ApiManager(retrofitInstance).restApiService
}