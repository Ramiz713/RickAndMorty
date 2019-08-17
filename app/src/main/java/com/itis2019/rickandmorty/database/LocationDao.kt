package com.itis2019.rickandmorty.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis2019.rickandmorty.entities.Location

@Dao
interface LocationDao {

    @Query("SELECT * FROM location_data")
    fun getAll(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locationData: List<Location>)

    @Query("DELETE FROM location_data")
    fun deleteAll()
}
