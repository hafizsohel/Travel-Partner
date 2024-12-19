package com.example.travelpartner.utils

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.travelpartner.R
import com.example.travelpartner.fragment.LocationsDetailFragment
import com.example.travelpartner.model.DestinationModel

private const val TAG = "GetLocationsHelper"
object GetLocationsHelper {

    fun navigateToLocationDetailFragment(fragmentManager: FragmentManager, selectedLocation: DestinationModel) {
        val bundle = Bundle().apply {
            putString("name", selectedLocation.name)
            putString("imageUrl", selectedLocation.imageUrl)
            putString("bn_desc", selectedLocation.bn_desc)
            putString("rating", selectedLocation.rating)
            putString("lat", selectedLocation.lat)
            putString("long", selectedLocation.long)
        }

        val fragment = LocationsDetailFragment().apply {
            arguments = bundle
        }

        fragmentManager.beginTransaction()
            .replace(R.id.FrameLayoutID, fragment)
            .addToBackStack(null)
            .commit()
    }
}
