package com.itis2019.rickandmorty.di.component

import com.itis2019.rickandmorty.di.module.CharactersModule
import com.itis2019.rickandmorty.di.module.LocationsModule
import com.itis2019.rickandmorty.di.module.RepositoryModule
import com.itis2019.rickandmorty.di.scope.RepositoryScope
import dagger.Subcomponent

@Subcomponent(modules = [RepositoryModule::class])
@RepositoryScope
interface RepositorySComponent {

    fun plusCharactersSComponent(charactersModule: CharactersModule): CharactersSComponent

    fun plusLocationsSComponent(locationsModule: LocationsModule): LocationsSComponent
}