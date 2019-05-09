package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import kotlinx.coroutines.Deferred

interface Repository {
    suspend fun getCharactersPageAsync(pageCount: Int): Deferred<Page<Character>>
    suspend fun getLocationsPageAsync(pageCount: Int): Deferred<Page<Location>>
    fun getCachedCharacters(): List<Character>
    fun getCachedLocations(): List<Location>
    fun cacheCharacters(characters: List<Character>)
    fun rewriteCacheCharacters(characters: List<Character>)
    fun cacheLocations(locations: List<Location>)
    fun rewriteCacheLocations(locations: List<Location>)
}
