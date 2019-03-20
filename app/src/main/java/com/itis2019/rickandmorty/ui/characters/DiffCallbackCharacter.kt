package com.itis2019.rickandmorty.ui.characters

import android.support.v7.util.DiffUtil
import com.itis2019.rickandmorty.entities.Character

class DiffCallbackCharacter : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}
