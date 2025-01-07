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
import com.example.travelpartner.model.RiverModel

class RiverAdapter(
    private val context: Context,
    private val riversList: MutableList<RiverModel>,
) : RecyclerView.Adapter<RiverAdapter.RiverViewHolder>() {
    var onItemClicked : ((RiverModel) ->Unit)? = null

    fun updateData(newList: List<RiverModel>) {
        riversList.clear()
        riversList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class RiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageLocation)
        val titleTextView: TextView = view.findViewById(R.id.textLocationName)
        val addressTextView: TextView = view.findViewById(R.id.textLocationAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiverViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
        return RiverViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiverViewHolder, position: Int) {
        val restaurant = riversList[position]
        holder.titleTextView.text = restaurant.name
        holder.addressTextView.text = restaurant.address
        Glide.with(context).load(restaurant.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(restaurant)
        }
    }
    override fun getItemCount(): Int = riversList.size
}
