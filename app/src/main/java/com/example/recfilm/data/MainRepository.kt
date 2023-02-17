package com.example.recfilm.data

import android.content.ContentValues
import android.database.Cursor
import com.example.recfilm.data.db.DatabaseHelper
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.data.dao.FilmDao
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Для запросов с БД нужен отдельлный поток
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films) //кладем список в БД в отдельном потоке
        }
    }

    fun getAllFromDB(): List<Film> {
        return filmDao.getCachedFilms()  // забирает все вильмы из БД
    }
}