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
import com.example.travelpartner.model.ResortModel

class ResortAdapter(
    private val context: Context,
    private val resortsList: MutableList<ResortModel>,
) : RecyclerView.Adapter<ResortAdapter.ResortViewHolder>() {
    var onItemClicked : ((ResortModel) ->Unit)? = null

    fun updateData(newList: List<ResortModel>) {
        resortsList.clear()
        resortsList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ResortViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageLocation)
        val titleTextView: TextView = view.findViewById(R.id.textLocationName)
        val addressTextView: TextView = view.findViewById(R.id.textLocationAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResortViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
        return ResortViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResortViewHolder, position: Int) {
        val resort = resortsList[position]
        holder.titleTextView.text = resort.name
        holder.addressTextView.text = resort.address
        Glide.with(context).load(resort.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(resort)
        }
    }
    override fun getItemCount(): Int = resortsList.size
}


