package com.example.recfilm.di

import com.example.recfilm.di.modules.DatabaseModule
import com.example.recfilm.di.modules.DomainModule
import com.example.recfilm.di.modules.RemoteModule
import com.example.recfilm.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}