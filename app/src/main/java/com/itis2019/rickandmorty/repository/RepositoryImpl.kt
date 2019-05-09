package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import com.itis2019.rickandmorty.repository.database.CharacterDao
import com.itis2019.rickandmorty.repository.database.LocationDao
import kotlinx.coroutines.Deferred

class RepositoryImpl(
    private val apiService: RickAndMortyApiService,
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao
) : Repository {

    override suspend fun getCharactersPageAsync(pageCount: Int): Deferred<Page<Character>> =
        apiService.getCharactersListAsync(pageCount)

    override suspend fun getLocationsPageAsync(pageCount: Int): Deferred<Page<Location>> =
        apiService.getLocationsListAsync(pageCount)

    override fun getCachedCharacters(): List<Character> = characterDao.getAll()

    override fun getCachedLocations(): List<Location> = locationDao.getAll()

    override fun cacheCharacters(characters: List<Character>) {
        characterDao.insertAll(characters)
    }

    override fun cacheLocations(locations: List<Location>) {
        locationDao.insertAll(locations)
    }

    override fun rewriteCacheCharacters(characters: List<Character>) {
        characterDao.deleteAll()
        characterDao.insertAll(characters)
    }

    override fun rewriteCacheLocations(locations: List<Location>) {
        locationDao.deleteAll()
        locationDao.insertAll(locations)
    }
}
