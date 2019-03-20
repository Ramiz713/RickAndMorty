package com.itis2019.rickandmorty.di.component

import com.itis2019.rickandmorty.di.module.CharactersModule
import com.itis2019.rickandmorty.di.scope.CharactersScope
import com.itis2019.rickandmorty.ui.characters.CharacterFragment
import dagger.Subcomponent

@Subcomponent(modules = [CharactersModule::class])
@CharactersScope
interface CharactersSComponent {

    fun inject(characterFragment: CharacterFragment)
}
