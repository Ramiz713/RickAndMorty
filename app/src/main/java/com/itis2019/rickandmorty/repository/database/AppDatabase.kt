package com.itis2019.rickandmorty.repository.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location

@Database(entities = [Character::class, Location::class], version = 1)
@TypeConverters(EpisodesConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}
