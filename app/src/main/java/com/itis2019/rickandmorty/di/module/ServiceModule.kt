package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.repository.RickAndMortyApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApiService(retrofit: Retrofit): RickAndMortyApiService =
        retrofit.create(RickAndMortyApiService::class.java)
}
