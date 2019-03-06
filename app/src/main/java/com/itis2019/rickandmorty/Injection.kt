package com.itis2019.rickandmorty

import android.content.Context
import com.itis2019.rickandmorty.repository.RepositoryImpl
import com.itis2019.rickandmorty.repository.database.AppDatabase

object Injection {
    fun provideRickAndMortyRepository(context: Context?) =
        RepositoryImpl(AppDatabase.getInstance(context))
}
