package com.itis2019.rickandmorty.model.character

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_data")
    fun getAll(): List<Character>

    @Insert()
    fun insertAll(characterData: List<Character>)

    @Query("DELETE FROM character_data")
    fun deleteAll()
}
