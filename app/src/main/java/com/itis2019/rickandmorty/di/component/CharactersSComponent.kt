package com.itis2019.rickandmorty.di.component

import com.itis2019.rickandmorty.characters.CharacterFragment
import com.itis2019.rickandmorty.di.module.CharactersModule
import com.itis2019.rickandmorty.di.scope.CharactersScope
import dagger.Subcomponent

@Subcomponent(modules = [CharactersModule::class])
@CharactersScope
interface CharactersSComponent {

    fun inject(characterFragment: CharacterFragment)
}