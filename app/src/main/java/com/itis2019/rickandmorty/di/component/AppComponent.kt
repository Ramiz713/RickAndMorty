package com.itis2019.rickandmorty.di.component

import android.content.Context
import com.itis2019.rickandmorty.di.module.*
import com.itis2019.rickandmorty.repository.RickAndMortyApiService
import com.itis2019.rickandmorty.repository.database.AppDatabase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class, ServiceModule::class, RoomModule::class])
interface AppComponent {

    fun provideApp(): Context

    fun rickAndMortyApiService(): RickAndMortyApiService

    fun provideAppDatabase(): AppDatabase

    fun plusRepositoryComponent(repositoryModule: RepositoryModule): RepositorySComponent
}
