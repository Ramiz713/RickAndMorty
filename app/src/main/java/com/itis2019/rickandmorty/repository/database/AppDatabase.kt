package com.itis2019.rickandmorty.repository.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location

@Database(entities = [Character::class, Location::class], version = 1)
@TypeConverters(EpisodesConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "rick_and_morty_app.db"

        fun getInstance(context: Context?): AppDatabase? {
            if (INSTANCE == null && context != null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
