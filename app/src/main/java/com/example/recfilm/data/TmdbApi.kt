package com.example.recfilm.data

import com.example.recfilm.data.Entity.TmdbResultsDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/{category}")
    fun getFilms(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Call<TmdbResultsDto>

    //поисковый запрос на сервер для поиска
    @GET("3/search/movie")
    fun getFilmFromSearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Observable<TmdbResultsDto>
}