package com.example.recfilm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.data.dao.FilmDao

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}