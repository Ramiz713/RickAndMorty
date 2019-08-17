package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.api.RickAndMortyApiService
import com.itis2019.rickandmorty.database.CharacterDao
import com.itis2019.rickandmorty.database.LocationDao
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(
    private val apiService: RickAndMortyApiService,
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao
) : Repository {

    override fun getCharactersPage(pageCount: Int): Single<Page<Character>> =
        apiService.getCharactersList(pageCount)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { cacheCharacters(it.results) }

    override fun getLocationsPage(pageCount: Int): Single<Page<Location>> =
        apiService.getLocationsList(pageCount)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { cacheLocations(it.results) }

    override fun getCachedCharacters(): List<Character> = characterDao.getAll()

    override fun getCachedLocations(): List<Location> = locationDao.getAll()

    private fun cacheCharacters(characters: List<Character>) = characterDao.insertAll(characters)

    private fun cacheLocations(locations: List<Location>) = locationDao.insertAll(locations)
}
