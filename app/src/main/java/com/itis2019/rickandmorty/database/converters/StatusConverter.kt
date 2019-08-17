package com.itis2019.rickandmorty.database.converters

import androidx.room.TypeConverter
import com.itis2019.rickandmorty.entities.Status

class StatusConverter {

    @TypeConverter
    fun statusToString(status: Status): String = status.name

    @TypeConverter
    fun stringToStatus(name: String): Status = Status.valueOf(name)
}
