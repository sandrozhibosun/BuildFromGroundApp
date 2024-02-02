package com.example.buildfromgroundapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.buildfromgroundapp.core.data.database.AppDatabase
import com.example.buildfromgroundapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Constants.ROOMDB_DBNAME
    )
        // .addMigrations(migration1To2)
        .build()


}