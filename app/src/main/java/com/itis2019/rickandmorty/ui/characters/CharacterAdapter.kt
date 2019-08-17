package com.itis2019.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.entities.Character
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.character_item.*
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterAdapter(private val listener: (Int, ImageView) -> Unit) :
    ListAdapter<Character, CharacterAdapter.CharacterHolder>(DIFF_CALLBACK) {

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

    inner class CharacterHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: Character): Unit = with(item) {
            tv_character_name.text = name
            Glide.with(containerView)
                .load(image)
                .placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image_character)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}
