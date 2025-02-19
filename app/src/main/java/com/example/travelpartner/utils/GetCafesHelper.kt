package com.example.travelpartner.utils

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.travelpartner.R
import com.example.travelpartner.fragment.CafeDetailFragment
import com.example.travelpartner.model.CafeModel

object GetCafesHelper {
    fun navigateToCafeDetailFragment(fragmentManager: FragmentManager, selectedLocation: CafeModel) {
        val bundle = Bundle().apply {
            putString("name", selectedLocation.name)
            putString("imageUrl", selectedLocation.imageUrl)
            putString("rating", selectedLocation.rating)
            putString("bn_desc", selectedLocation.bn_desc)
            putString("latitude", selectedLocation.latitude)
            putString("longitude", selectedLocation.longitude)
        }
        val fragment = CafeDetailFragment().apply {
            arguments = bundle
        }
        fragmentManager.beginTransaction()
            .replace(R.id.FrameLayoutID, fragment)
            .addToBackStack(null)
            .commit()
    }
}