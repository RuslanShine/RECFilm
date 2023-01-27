package com.example.recfilm


import android.app.Application
import com.example.recfilm.data.ApiConstants
import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.TmdbApi
import com.example.recfilm.di.DI
import com.example.recfilm.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(DI.mainModule))
        }
    }
}