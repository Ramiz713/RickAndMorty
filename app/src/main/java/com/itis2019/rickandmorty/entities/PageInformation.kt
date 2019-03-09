package com.itis2019.rickandmorty.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PageInformation(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
) : Parcelable
