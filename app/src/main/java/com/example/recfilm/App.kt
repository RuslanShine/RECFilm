package com.example.recfilm

import android.app.Application
import com.example.recfilm.di.AppComponent
import com.example.recfilm.di.DaggerAppComponent
import com.example.recfilm.di.modules.DatabaseModule
import com.example.recfilm.di.modules.DomainModule
import com.example.remote_module.DaggerRemoteComponent
import com.example.remote_module.RemoteModule


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}