package com.example.recfilm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recfilm.data.Entity.Film

@Dao
interface FilmDao {
    //Сделаем запрос на всю таблицу
    @Query("SELECT * FROM cached_film")
    fun getCachedFilms(): List<Film>  // забирает все вильмы из БД

    //Кладём списов в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)
}