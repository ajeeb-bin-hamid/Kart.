package com.ajeeb.kart.main.data.di

import com.ajeeb.kart.common.data.db.KartDb
import com.ajeeb.kart.main.data.repository.MainKartDbRepositoryImpl
import com.ajeeb.kart.main.domain.repository.MainKartDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainKartDbRepositoryModule {

    @Provides
    @Singleton
    fun provideMainExpensesDbRepository(kartDb: KartDb): MainKartDbRepository {
        return MainKartDbRepositoryImpl(kartDb.mainKartDbDao)
    }
}