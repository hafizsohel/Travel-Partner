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
import com.example.travelpartner.adapter.HotelAdapter.HotelViewHolder
import com.example.travelpartner.model.HotelModel

class HotelAdapter(
private val context: Context,
private val hotelsList: MutableList<HotelModel>,
) : RecyclerView.Adapter<HotelViewHolder>() {
    var onItemClicked : ((HotelModel) ->Unit)? = null

    fun updateData(newList: List<HotelModel>) {
        hotelsList.clear()
        hotelsList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageLocation)
        val titleTextView: TextView = view.findViewById(R.id.textLocationName)
        val addressTextView: TextView = view.findViewById(R.id.textLocationAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotelsList[position]
        holder.titleTextView.text = hotel.name
        holder.addressTextView.text = hotel.address
        Glide.with(context).load(hotel.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(hotel)
        }
    }
    override fun getItemCount(): Int = hotelsList.size
}


