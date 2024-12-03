package com.example.travelpartner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelpartner.R
import com.example.travelpartner.model.LocationModel

class LocationAdapter(private val locations: List<LocationModel>) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textLocationName)
        val address: TextView = view.findViewById(R.id.textLocationAddress)
        val image: ImageView = view.findViewById(R.id.imageLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.name.text = location.name
        holder.address.text = location.address
        Glide.with(holder.image.context)
            .load(location.imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.image)
    }

    override fun getItemCount(): Int = locations.size
}
