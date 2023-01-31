package com.example.recfilm

import android.app.Application
import com.example.recfilm.di.AppComponent
import com.example.recfilm.di.DaggerAppComponent


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}