package com.itis2019.rickandmorty.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.entities.Location
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.location_item.*

class LocationAdapter :
    ListAdapter<Location, LocationAdapter.LocationHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder =
        LocationHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.location_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class LocationHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: Location): Unit = with(item) {
            tv_location_name.text = name
            tv_dimension.text = dimension
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean =
                oldItem == newItem
        }
    }
}
