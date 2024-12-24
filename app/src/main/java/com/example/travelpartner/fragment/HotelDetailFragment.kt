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
import com.example.travelpartner.R
import com.example.travelpartner.adapter.HotelAdapter
import com.example.travelpartner.adapter.ResortAdapter
import com.example.travelpartner.databinding.FragmentHotelDetailBinding
import com.example.travelpartner.databinding.FragmentResortDetailBinding
import com.example.travelpartner.model.HotelModel
import com.example.travelpartner.model.ResortModel

class HotelDetailFragment : Fragment() {
    private lateinit var binding: FragmentHotelDetailBinding
    private val hotelList = mutableListOf<HotelModel>()
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelDetailBinding.inflate(layoutInflater)
        hotelAdapter = HotelAdapter(requireContext(), hotelList)
        hotelAdapter = hotelAdapter

        val name = arguments?.getString("name") ?: "No Name"
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        val description = arguments?.getString("bn_desc") ?: "No Description"
        val rating = arguments?.getString("rating") ?: "No Rating"
        val latitude = arguments?.getString("latitude") ?: "No Latitude"
        val longitude = arguments?.getString("longitude") ?: "No Longitude"

        binding.hotelTitle.text = name
        binding.hotelDescription.text = description
        binding.hotelRating.text = rating
        Glide.with(requireContext()).load(imageUrl).into(binding.hotelImage)

        val locationCoordinates = "Lat: $latitude, Long: $longitude"
        binding.btnSeeHotelLocation.text = "See Location"

        binding.btnSeeHotelLocation.setOnClickListener {
            val buttonText = locationCoordinates
            val regex = "Lat:\\s*([\\d.-]+),\\s*Long:\\s*([\\d.-]+)".toRegex()
            val matchResult = regex.find(buttonText)

            if (matchResult != null) {
                val latitude = matchResult.groupValues[1]
                val longitude = matchResult.groupValues[2]

                val locationUri =
                    Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($name)")
                try {
                    val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        requireContext(),
                        "Google Maps is not installed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Invalid location format", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarHotelDetails)
        binding.toolbarHotelDetails.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}