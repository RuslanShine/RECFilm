package com.example.recfilm.di.modules

import android.content.Context
import com.example.recfilm.data.MainRepository
import com.example.remote_module.TmdbApi
import com.example.recfilm.data.preferenes.PreferenceProvider
import com.example.recfilm.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePreference(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(
        repository: MainRepository,
        tmdbApi: com.example.remote_module.TmdbApi,
        preferenceProvider: PreferenceProvider,
    ) = Interactor(repo = repository, retrofitService = tmdbApi, preferences = preferenceProvider)
}