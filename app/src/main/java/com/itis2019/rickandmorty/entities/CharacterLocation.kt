package com.itis2019.rickandmorty.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterLocation(
    val name: String,
    val url: String
) : Parcelable
