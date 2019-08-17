package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.api.RickAndMortyApiService
import com.itis2019.rickandmorty.database.CharacterDao
import com.itis2019.rickandmorty.database.LocationDao
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        service: RickAndMortyApiService,
        characterDao: CharacterDao,
        locationDao: LocationDao
    ): Repository =
        RepositoryImpl(service, characterDao, locationDao)
}
