package com.example.travelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelpartner.R
import com.example.travelpartner.model.LocationModel

class LocationAdapter(
    private val context: Context,
    private var destinationList: MutableList<LocationModel>
) : RecyclerView.Adapter<LocationAdapter.DestinationViewHolder>() {
    var onItemClicked : ((LocationModel) ->Unit)? = null

    fun updateData(newList: List<LocationModel>) {
        destinationList.clear()
        destinationList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class DestinationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageLocation)
        val titleTextView: TextView = view.findViewById(R.id.textLocationName)
        val addressTextView:TextView = view.findViewById(R.id.textLocationAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = destinationList[position]
        holder.titleTextView.text = destination.name
        holder.addressTextView.text = destination.address
        Glide.with(context).load(destination.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(destination)
        }
    }
    override fun getItemCount(): Int = destinationList.size
}

