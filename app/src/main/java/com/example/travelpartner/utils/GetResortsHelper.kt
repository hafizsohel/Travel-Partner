package com.example.travelpartner.utils

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.travelpartner.R
import com.example.travelpartner.fragment.ResortDetailFragment
import com.example.travelpartner.model.ResortModel

object GetResortsHelper {
    fun navigateToResortDetailFragment(fragmentManager: FragmentManager, selectedLocation: ResortModel) {
        val bundle = Bundle().apply {
            putString("name", selectedLocation.name)
            putString("imageUrl", selectedLocation.imageUrl)
            putString("rating", selectedLocation.starRating)
            putString("bn_desc", selectedLocation.bn_desc)
            putString("latitude", selectedLocation.latitude)
            putString("longitude", selectedLocation.longitude)
        }

        val fragment = ResortDetailFragment().apply {
            arguments = bundle
        }

        fragmentManager.beginTransaction()
            .replace(R.id.FrameLayoutID, fragment)
            .addToBackStack(null)
            .commit()
    }
}