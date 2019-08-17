package com.itis2019.rickandmorty.api

import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character?")
    fun getCharactersList(
        @Query("page") page: Int
    ): Single<Page<Character>>

    @GET("location?")
    fun getLocationsList(@Query("page") page: Int): Single<Page<Location>>
}
