package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.repository.RepositoryImpl
import com.itis2019.rickandmorty.repository.RickAndMortyApiService
import com.itis2019.rickandmorty.repository.database.CharacterDao
import com.itis2019.rickandmorty.repository.database.LocationDao
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
