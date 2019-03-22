package com.itis2019.rickandmorty.ui.characters

import android.support.v4.view.ViewCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.entities.Character
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.character_item.*
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterAdapter(private val listener: (Int, ImageView) -> Unit) :
    ListAdapter<Character, CharacterAdapter.CharacterHolder>(DiffCallbackCharacter()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder =
        CharacterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.character_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        val image = holder.itemView.image_character
        holder.itemView.setOnClickListener { listener(position, image) }
        ViewCompat.setTransitionName(image, item.name)
    }

    class CharacterHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Character): Unit = with(item) {
            tv_character_name.text = name
            Glide.with(containerView).load(image).into(image_character)
        }
    }
}
