package com.itis2019.rickandmorty.model.character

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.itis2019.rickandmorty.model.EpisodesConverter
import kotlinx.android.parcel.Parcelize

data class CharacterList(
    val info: Info,
    val results: List<Character>
)

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
    val location: LocationOfCharacter,
    val name: String,
    @Embedded(prefix = "origin_")
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable

@Parcelize
data class LocationOfCharacter(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Origin(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
) : Parcelable
