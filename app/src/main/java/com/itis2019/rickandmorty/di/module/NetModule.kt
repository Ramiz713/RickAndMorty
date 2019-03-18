package com.itis2019.rickandmorty.di.module

import com.itis2019.rickandmorty.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        callAdapterFactory: RxJava2CallAdapterFactory,
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
