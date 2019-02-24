package com.itis2019.rickandmorty.main

import android.support.v4.view.ViewCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.model.Character
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_info.view.*
import kotlinx.android.synthetic.main.character_item.*

class CharacterAdapter(private val listener: (Int, ImageView) -> Unit) :
    ListAdapter<Character, CharacterAdapter.CharacterHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder =
        CharacterHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false))

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bind(getItem(position))
        val image = holder.itemView.image_character
        holder.itemView.setOnClickListener { listener(position, image) }
        ViewCompat.setTransitionName(image, getItem(position).name)
    }

    class CharacterHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Character) = with(item) {
            tv_character_name.text = name
            Picasso.get().load(image).into(image_character)
        }
    }
}
