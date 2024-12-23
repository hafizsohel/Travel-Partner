package com.example.travelpartner.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.travelpartner.adapter.DestinationAdapter
import com.example.travelpartner.databinding.FragmentLocationsDetailBinding
import com.example.travelpartner.model.LocationModel

private const val TAG = "LocationsDetailFragment"
class LocationDetailFragment : Fragment() {
    private lateinit var binding:FragmentLocationsDetailBinding
    private val destinationList = mutableListOf<LocationModel>()
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

        val locationCoordinates = "Lat: $latitude, Long: $longitude"
        binding.btnSeeLocation.text = "See Location"

        binding.btnSeeLocation.setOnClickListener {
            val buttonText = locationCoordinates
            val regex = "Lat:\\s*([\\d.-]+),\\s*Long:\\s*([\\d.-]+)".toRegex()
            val matchResult = regex.find(buttonText)

            if (matchResult != null) {
                val latitude = matchResult.groupValues[1]
                val longitude = matchResult.groupValues[2]

                val locationUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($name)")

                try {
                    val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(requireContext(), "Google Maps is not installed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Invalid location format", Toast.LENGTH_SHORT).show()
            }
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
}