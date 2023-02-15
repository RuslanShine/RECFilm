package com.example.recfilm.data

import android.content.ContentValues
import android.database.Cursor
import com.example.recfilm.data.db.DatabaseHelper
import com.example.recfilm.domain.Film

class MainRepository(databaseHelper: DatabaseHelper) {
    //Инициализируем объект для взаимодействии с БД
    private val sqlDb = databaseHelper.readableDatabase

    //Создаём курсор для обработки запросов из БД
    private lateinit var cursor: Cursor

    //Кладём фильмы в БД
    fun putToDb(film: Film) {
        //Создаём обьект для хранения пары ключ-значения, для того
        //чтобы класть нужные данные в нужные столбцы
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, film.title)
            put(DatabaseHelper.COLUMN_POSTER, film.poster)
            put(DatabaseHelper.COLUMN_DESCRIPTION, film.description)
            put(DatabaseHelper.COLUMN_RATING, film.rating)
        }
        //Кладём фильм в БД
        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }

    //Достём фильмы из БД
    fun getAllFromDB(): List<Film> {
        //Создаём курсор на основании запроса "Получить всё из таблицы"
        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        //Сюда сохраняем результат получения данный
        val result = mutableListOf<Film>()
        //Проверяем, есть ли хоть однастрока в ответе на запроса
        if (cursor.moveToFirst()) {
            //Инициализируем по таблице пока есть записи, и создаём на основании объект Film
            do {
                val title = cursor.getString(COLUMN_INDEX_TITLE)
                val poster = cursor.getString(COLUMN_INDEX_POSTER)
                val description = cursor.getString(COLUMN_INDEX_DESCRIPTION)
                val rating = cursor.getDouble(COLUMN_INDEX_RATING)

                result.add(Film(title, poster, description, rating))
            } while (cursor.moveToNext())
        }
        //Возвращем список фильмов
        return result
    }

    companion object {
        const val COLUMN_INDEX_TITLE = 1
        const val COLUMN_INDEX_POSTER = 2
        const val COLUMN_INDEX_DESCRIPTION = 3
        const val COLUMN_INDEX_RATING = 4
    }
}