package com.ajeeb.kart.common.data.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ajeeb.kart.common.data.db.KartDb
import com.ajeeb.kart.common.data.utils.KART_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonDataModule {

    @Provides
    @Singleton
    fun provideKartDb(context: Context): KartDb {
        val dbFile = context.getDatabasePath(KART_DB)
        return Room.databaseBuilder<KartDb>(
            context = context, name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver()).build()
    }
}