package com.itis2019.rickandmorty.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itis2019.rickandmorty.repository.database.EpisodesConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "character_data")
data class Character(
    val created: String,
    @TypeConverters(EpisodesConverter::class)
    val episode: List<String>,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    @Embedded(prefix = "location_")
    val location: CharacterLocation,
    val name: String,
    @Embedded(prefix = "origin_")
    val origin: CharacterLocation,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable {
    constructor() : this(
        "", ArrayList(),
        "", 0, "",
        CharacterLocation("", ""),
        "", CharacterLocation("", ""),
        "", "", "", ""
    )
}
