package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString(): String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory =
        CoroutineCallAdapterFactory()

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        callAdapterFactory: CoroutineCallAdapterFactory,
        @Named(NAME_BASE_URL) baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .baseUrl(baseUrl)
        .build()

    companion object {
        private const val NAME_BASE_URL = "BaseURL"
    }
}
