package com.itis2019.rickandmorty.model

import com.itis2019.rickandmorty.BuildConfig
import com.itis2019.rickandmorty.model.character.CharacterList
import com.itis2019.rickandmorty.model.location.LocationList
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character?")
    fun getCharactersList(
        @Query("page") page: Int
    ): Single<CharacterList>

    @GET("location?")
    fun getLocationsList(
        @Query("page") page: Int
    ): Single<LocationList>

    companion object ApiFactory {

        fun create(): RickAndMortyApiService = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
            .create(RickAndMortyApiService::class.java)
    }
}
