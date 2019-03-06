package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import io.reactivex.Single

interface Repository {
    fun getCharactersPage(pageCount: Int): Single<List<Character>>
    fun getLocationsPage(pageCount: Int): Single<List<Location>>
    fun getCachedCharacters(): List<Character>
    fun getCachedLocations(): List<Location>
    fun cacheCharacters(characters: List<Character>)
    fun rewriteCacheCharacters(characters: List<Character>)
    fun cacheLocations(locations: List<Location>)
    fun rewriteCacheLocations(locations: List<Location>)
}
