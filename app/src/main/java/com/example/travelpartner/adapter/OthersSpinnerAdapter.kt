/*
package com.example.travelpartner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.travelpartner.R

class OthersSpinnerAdapter(
    private val context: Context,
    private val data: List<Pair<String, Int>> // Pair of Text and Image Resource
) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Pair<String, Int> = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinnerText)
        val imageView = view.findViewById<ImageView>(R.id.spinnerArrow)

        val (text, imageRes) = getItem(position)

        textView.text = text
        imageView.setImageResource(imageRes)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Inflate dropdown view if needed
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.spinnerText)
        val imageView = view.findViewById<ImageView>(R.id.spinnerArrow)

        val (text, imageRes) = getItem(position)

        textView.text = text
        imageView.setImageResource(imageRes)

        return view
    }
}
*/
