package com.itis2019.rickandmorty.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.rickandmorty.entities.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_data")
    fun getAll(): List<Character>

    @Insert
    fun insertAll(characterData: List<Character>)

    @Query("DELETE FROM character_data")
    fun deleteAll()
}
