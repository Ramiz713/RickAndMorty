package com.itis2019.rickandmorty.locations

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.entities.Location
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.location_item.*

class LocationAdapter(private val listener: (Int) -> Unit) :
    ListAdapter<Location, LocationAdapter.LocationHolder>(DiffCallbackLocation()) {

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
        holder.itemView.setOnClickListener { listener(position) }
    }

    class LocationHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Location): Unit = with(item) {
            tv_location_name.text = name
            tv_dimension.text = dimension
        }
    }
}
