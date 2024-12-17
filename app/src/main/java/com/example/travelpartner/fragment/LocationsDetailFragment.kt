package com.example.travelpartner.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.travelpartner.adapter.DestinationAdapter
import com.example.travelpartner.databinding.FragmentLocationsDetailBinding
import com.example.travelpartner.model.DestinationModel

private const val TAG = "LocationsDetailFragment"
class LocationsDetailFragment : Fragment() {
    private lateinit var binding:FragmentLocationsDetailBinding
    private val destinationList = mutableListOf<DestinationModel>()
    private lateinit var destinationAdapter: DestinationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=FragmentLocationsDetailBinding.inflate(layoutInflater)

        destinationAdapter= DestinationAdapter(requireContext(), destinationList)
            destinationAdapter = destinationAdapter


        val name = arguments?.getString("name") ?: "No Name"
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        val description = arguments?.getString("bn_desc") ?: "No Description"
        val rating = arguments?.getString("rating") ?: "No Rating"
        val latitude = arguments?.getString("lat") ?: "No Latitude"
        val longitude = arguments?.getString("long") ?: "No Longitude"

        binding.placeTitle.text = name
        binding.placeDescription.text = description
        binding.placeRating.text = rating
        Glide.with(requireContext()).load(imageUrl).into(binding.placeImage)

        if (latitude != null && longitude != null) {
            binding.seeLocation.setOnClickListener {
                openGoogleMaps(latitude.toDouble(), longitude.toDouble())
            }
        } else {
            Toast.makeText(requireContext(), "Coordinates not available", Toast.LENGTH_SHORT).show()
        }

        setupToolbar()
        return binding.root
    }
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarDetails)
        binding.toolbarDetails.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openGoogleMaps(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        val activities = requireContext().packageManager.queryIntentActivities(mapIntent, 0)
        if (activities.isNotEmpty()) {
            for (activity in activities) {
                Log.d("Maps", "Activity: ${activity.activityInfo.packageName}")
            }
            startActivity(mapIntent)
        } else {
            Toast.makeText(requireContext(), "No map application found.", Toast.LENGTH_SHORT).show()
        }
    }

}