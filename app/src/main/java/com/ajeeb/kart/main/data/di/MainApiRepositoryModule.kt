package com.ajeeb.kart.main.data.di

import com.ajeeb.kart.common.data.utils.ApiClient
import com.ajeeb.kart.main.data.network.MainApiService
import com.ajeeb.kart.main.data.repository.MainApiRepositoryImpl
import com.ajeeb.kart.main.domain.repository.MainApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainApiRepositoryModule {

    @Provides
    @Singleton
    fun provideMainApiService(apiClient: ApiClient): MainApiService =
        Retrofit.Builder().baseUrl("https://c01a428178544c239bb346a2e3a2293f.api.mockbin.io")
            .client(apiClient.instance)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideMainApiRepository(
        apiService: MainApiService
    ): MainApiRepository = MainApiRepositoryImpl(
        mainApiService = apiService
    )
}