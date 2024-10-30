package com.example.viewmodeldice.di

import android.content.Context
import androidx.room.Room
import com.example.viewmodeldice.data.AppDatabase
import com.example.viewmodeldice.data.db.ResultsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dices_db"
        ).build()

    @Provides
    @Singleton
    fun providesResultDao(appDatabase: AppDatabase) : ResultsDao =
        appDatabase.resultDao()

}