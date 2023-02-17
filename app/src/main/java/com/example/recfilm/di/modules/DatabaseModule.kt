package com.example.recfilm.di.modules

import android.content.Context
import androidx.room.Room
import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.dao.FilmDao
import com.example.recfilm.data.db.AppDatabase
import com.example.recfilm.data.db.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"

        ).build().filmDao()


    @Singleton
    @Provides
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}