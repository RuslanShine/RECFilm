package com.example.recfilm.utils


import com.example.recfilm.data.Entity.Film
import com.example.remote_module.entity.TmdbFilm

object Converter {
    fun convertApiListDtoList(list: List<TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(
                Film(
                    title = it.title,
                    poster = it.posterPath,
                    description = it.overview,
                    rating = it.voteAverage,
                    isInFavorites = false
                )
            )
        }
        return result
    }
}