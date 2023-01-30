package com.example.recfilm.di.modules

import com.example.recfilm.data.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModules {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}