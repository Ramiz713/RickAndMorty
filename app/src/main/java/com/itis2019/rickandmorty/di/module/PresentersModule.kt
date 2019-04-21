package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.characters.CharacterPresenter
import com.itis2019.rickandmorty.ui.locations.LocationPresenter
import com.itis2019.rickandmorty.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class PresentersModule {

    @Provides
    @Singleton
    fun provideCharactersPresenter(repository: Repository, router: Router): CharacterPresenter =
        CharacterPresenter(repository, router)

    @Provides
    @Singleton
    fun provideLocationsPresenter(repository: Repository): LocationPresenter =
        LocationPresenter(repository)

    @Provides
    @Singleton
    fun provideMainPresenter(router: Router): MainPresenter =
        MainPresenter(router)
}
