package com.itis2019.rickandmorty.entities

import com.google.gson.annotations.SerializedName

enum class Status(val value: String) {
    @SerializedName("Alive")
    ALIVE("Alive"),

    @SerializedName("Dead")
    DEAD("Dead"),

    @SerializedName("unknown")
    UNKNOWN("unknown")
}
