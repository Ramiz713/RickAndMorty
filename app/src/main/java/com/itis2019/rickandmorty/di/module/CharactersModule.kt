package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.di.scope.CharactersScope
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.characters.CharacterPresenter
import dagger.Module
import dagger.Provides

@Module
class CharactersModule {

    @Provides
    @CharactersScope
    fun provideCharactersPresenter(repository: Repository): CharacterPresenter =
        CharacterPresenter(repository)

}
