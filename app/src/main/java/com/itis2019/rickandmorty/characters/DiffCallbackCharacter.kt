package com.itis2019.rickandmorty.characters

import android.support.v7.util.DiffUtil
import com.itis2019.rickandmorty.model.character.Character

class DiffCallbackCharacter : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}
