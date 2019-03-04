package com.itis2019.rickandmorty.model.location

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * FROM location_data")
    fun getAll(): List<Location>

    @Insert()
    fun insertAll(locationData: List<Location>)

    @Query("DELETE FROM location_data")
    fun deleteAll()
}