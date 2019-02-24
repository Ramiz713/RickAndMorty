package com.itis2019.rickandmorty.main

import android.support.v7.util.DiffUtil
import com.itis2019.rickandmorty.model.Character

class DiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}
