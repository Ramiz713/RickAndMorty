package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.di.scope.LocationsScope
import com.itis2019.rickandmorty.repository.Repository
import com.itis2019.rickandmorty.ui.locations.LocationPresenter
import dagger.Module
import dagger.Provides

@Module
class LocationsModule {

    @Provides
    @LocationsScope
    fun provideLocationsPresenter(repository: Repository): LocationPresenter =
        LocationPresenter(repository)
}
