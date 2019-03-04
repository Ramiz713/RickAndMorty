package com.itis2019.rickandmorty.model

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EpisodesConverter {
    @TypeConverter
    fun serialize(listOfEpisodes: List<String>): String =
        Gson().toJson(listOfEpisodes)

    @TypeConverter
    fun deserialize(jsonOfEpisodes: String): List<String> =
        Gson().fromJson(jsonOfEpisodes, object : TypeToken<List<String>>() {}.type)
}
