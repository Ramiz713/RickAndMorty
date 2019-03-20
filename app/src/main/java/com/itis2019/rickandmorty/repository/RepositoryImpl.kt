package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.repository.database.CharacterDao
import com.itis2019.rickandmorty.repository.database.LocationDao
import io.reactivex.Single

class RepositoryImpl(
    private val apiService: RickAndMortyApiService,
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao
) : Repository {

    override fun getCharactersPage(pageCount: Int): Single<List<Character>> =
        apiService.getCharactersList(pageCount)
            .map { it.results }

    override fun getLocationsPage(pageCount: Int): Single<List<Location>> =
        apiService.getLocationsList(pageCount)
            .map { it.results }

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
