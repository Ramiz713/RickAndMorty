package com.itis2019.rickandmorty.model.location

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.itis2019.rickandmorty.model.character.Info
import kotlinx.android.parcel.Parcelize

data class LocationList(
    val info: Info,
    val results: List<Location>
)

@Parcelize
@Entity(tableName = "location_data")
data class Location(
    val created: String,
    val dimension: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
) : Parcelable
