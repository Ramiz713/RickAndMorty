package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import io.reactivex.Single

interface Repository {

    fun getCharactersPage(pageCount: Int): Single<Page<Character>>

    fun getLocationsPage(pageCount: Int): Single<Page<Location>>

    fun getCachedCharacters(): List<Character>

    fun getCachedLocations(): List<Location>
}
