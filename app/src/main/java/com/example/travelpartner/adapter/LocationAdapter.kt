package com.example.travelpartner.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelpartner.databinding.AddressListBinding

class Location {
    val name: String = ""
    val address: String = ""
    val imageUrl: String = ""
}

class LocationAdapter(private val locations: List<Location>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = AddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class LocationViewHolder(private val binding: AddressListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.tvLocationName.text = location.name
            binding.tvLocationAddress.text = location.address
            Glide.with(binding.root.context)
                .load(location.imageUrl)
                .into(binding.ivLocationImage)

            // Handle item click if needed
            binding.root.setOnClickListener {
                // Handle click events if necessary
            }
        }
    }
}
