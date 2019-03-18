package com.itis2019.rickandmorty.di.component

import com.itis2019.rickandmorty.di.module.LocationsModule
import com.itis2019.rickandmorty.di.scope.LocationsScope
import com.itis2019.rickandmorty.locations.LocationFragment
import dagger.Subcomponent

@Subcomponent(modules = [LocationsModule::class])
@LocationsScope
interface LocationsSComponent {

    fun inject(characterFragment: LocationFragment)
}
