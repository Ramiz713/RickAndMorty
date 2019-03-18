package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.di.scope.RepositoryScope
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.repository.RepositoryImpl
import com.itis2019.rickandmorty.repository.RickAndMortyApiService
import com.itis2019.rickandmorty.repository.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @RepositoryScope
    fun provideRepository(service: RickAndMortyApiService, appDatabase: AppDatabase): Repository =
        RepositoryImpl(service, appDatabase)
}
