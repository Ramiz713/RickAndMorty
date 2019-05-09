package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character?")
    fun getCharactersListAsync(@Query("page") page: Int): Deferred<Page<Character>>

    @GET("location?")
    fun getLocationsListAsync(@Query("page") page: Int): Deferred<Page<Location>>
}
