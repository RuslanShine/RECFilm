package com.example.remote_module

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}