package com.example.recfilm.di

import com.example.recfilm.BuildConfig
import com.example.recfilm.data.ApiConstants
import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.TmdbApi
import com.example.recfilm.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DI {
    val mainModule = module {
        single { MainRepository() }
        //Создаем объект для получения данных из сети
        single<TmdbApi> {
            val okHttpClient = OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                })
                .build()
            //Ретрофит
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_USL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            //Сервис с методами для запросов
            retrofit.create(TmdbApi::class.java)
        }
        //Интерактор
        single { Interactor(get(),get()) }
    }
}