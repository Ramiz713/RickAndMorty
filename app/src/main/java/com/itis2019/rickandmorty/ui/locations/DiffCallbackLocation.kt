package com.itis2019.rickandmorty.ui.locations

import android.support.v7.util.DiffUtil
import com.itis2019.rickandmorty.entities.Location

class DiffCallbackLocation : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean =
        oldItem == newItem
}
