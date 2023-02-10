package com.example.recfilm.di.modules

import android.content.Context
import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.db.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}