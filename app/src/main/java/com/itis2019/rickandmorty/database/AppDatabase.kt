package com.itis2019.rickandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis2019.rickandmorty.database.converters.EpisodesConverter
import com.itis2019.rickandmorty.database.converters.StatusConverter
import com.itis2019.rickandmorty.entities.Character
import com.itis2019.rickandmorty.entities.Location

@Database(entities = [Character::class, Location::class], version = 1)
@TypeConverters(EpisodesConverter::class, StatusConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun locationDao(): LocationDao
}
