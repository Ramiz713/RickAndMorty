package com.itis2019.rickandmorty.repository

import com.itis2019.rickandmorty.BuildConfig
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location
import com.itis2019.rickandmorty.entities.Page
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character?")
    fun getCharactersList(@Query("page") page: Int): Single<Page<Character>>

    @GET("location?")
    fun getLocationsList(@Query("page") page: Int): Single<Page<Location>>

    companion object ApiFactory {

        fun create(): RickAndMortyApiService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
            .create(RickAndMortyApiService::class.java)
    }
}
