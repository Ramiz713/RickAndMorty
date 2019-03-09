package com.itis2019.rickandmorty.entities

import com.google.gson.annotations.SerializedName

data class Page<T>(
    @SerializedName("info")
    val pageInformation: PageInformation,
    val results: List<T>
)
