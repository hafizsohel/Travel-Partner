package com.example.travelpartner.utils

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.travelpartner.R
import com.example.travelpartner.fragment.LocationDetailFragment
import com.example.travelpartner.model.LocationModel

private const val TAG = "GetLocationsHelper"
object GetLocationsHelper {

    fun navigateToLocationDetailFragment(fragmentManager: FragmentManager, selectedLocation: LocationModel) {
        val bundle = Bundle().apply {
            putString("name", selectedLocation.name)
            putString("imageUrl", selectedLocation.imageUrl)
            putString("bn_desc", selectedLocation.bn_desc)
            putString("rating", selectedLocation.rating)
            putString("lat", selectedLocation.lat)
            putString("long", selectedLocation.long)
        }

        val fragment = LocationDetailFragment().apply {
            arguments = bundle
        }

        fragmentManager.beginTransaction()
            .replace(R.id.FrameLayoutID, fragment)
            .addToBackStack(null)
            .commit()
    }
}
