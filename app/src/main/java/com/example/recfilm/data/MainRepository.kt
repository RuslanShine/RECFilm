package com.example.recfilm.data

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.recfilm.data.db.DatabaseHelper
import com.example.recfilm.data.Entity.Film
import com.example.recfilm.data.dao.FilmDao
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Для запросов с БД нужен отдельлный поток
        filmDao.insertAll(films) //кладем список в БД в отдельном потоке
    }

    fun getAllFromDB(): Observable<List<Film>> =
        filmDao.getCachedFilms()  // забирает все вильмы из БД
}