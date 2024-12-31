package com.example.travelpartner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelpartner.R

class OptionListsAdapter(
    private val options: List<String>,
    private val onOptionClick: (String) -> Unit
) : RecyclerView.Adapter<OptionListsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textOption: TextView = itemView.findViewById(R.id.textViewOption)

        fun bind(option: String) {
            textOption.text = option
            itemView.setOnClickListener {
                onOptionClick(option)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size
}
