package com.itis2019.rickandmorty.di.module

import android.app.Application
import androidx.room.Room
import com.itis2019.rickandmorty.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideRoomDatabase() =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase) = database.characterDao()

    @Provides
    @Singleton
    fun provideLocationDao(database: AppDatabase) = database.locationDao()

    companion object {
        private const val DATABASE_NAME = "rick_and_morty_app.db"
    }
}
