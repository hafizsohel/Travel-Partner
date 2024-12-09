package com.example.travelpartner.adapter

import com.example.travelpartner.R
import com.example.travelpartner.model.DestinationModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class DestinationAdapter(
    private val context: Context,
    private val destinationList: List<DestinationModel>
) : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    inner class DestinationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.destination_image)
        val titleTextView: TextView = view.findViewById(R.id.destination_title)
       // val addressTextView: TextView = view.findViewById(R.id.destination_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_destination, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = destinationList[position]
        holder.titleTextView.text = destination.name
       // holder.addressTextView.text = destination.address
        Glide.with(context).load(destination.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = destinationList.size
}
