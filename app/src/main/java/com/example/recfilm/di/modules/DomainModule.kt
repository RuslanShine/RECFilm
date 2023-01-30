package com.example.recfilm.di.modules

import com.example.recfilm.data.MainRepository
import com.example.recfilm.data.TmdbApi
import com.example.recfilm.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) =
        Interactor(repo = repository, retrofitService = tmdbApi)
}