package com.example.travelpartner.utils

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.travelpartner.R
import com.example.travelpartner.fragment.HotelDetailFragment
import com.example.travelpartner.model.HotelModel

object GetHotelsHelper {
    fun navigateToHotelDetailFragment(fragmentManager: FragmentManager, selectedLocation: HotelModel) {
        val bundle = Bundle().apply {
            putString("name", selectedLocation.name)
            putString("imageUrl", selectedLocation.imageUrl)
            putString("rating", selectedLocation.rating)
            putString("bn_desc", selectedLocation.bn_desc)
            putString("latitude", selectedLocation.latitude)
            putString("longitude", selectedLocation.longitude)
        }
        val fragment = HotelDetailFragment().apply {
            arguments = bundle
        }
        fragmentManager.beginTransaction()
            .replace(R.id.FrameLayoutID, fragment)
            .addToBackStack(null)
            .commit()
    }
}