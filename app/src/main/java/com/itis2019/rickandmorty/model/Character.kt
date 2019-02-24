package com.itis2019.rickandmorty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CharacterList(
    val info: Info,
    val results: List<Character>
)

@Parcelize
data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable

@Parcelize
data class Location(
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
