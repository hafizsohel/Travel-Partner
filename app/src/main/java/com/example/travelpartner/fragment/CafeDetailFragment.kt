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
import com.example.travelpartner.adapter.CafeAdapter
import com.example.travelpartner.adapter.RestaurantAdapter
import com.example.travelpartner.databinding.FragmentCafeDetailBinding
import com.example.travelpartner.databinding.FragmentRestaurantDetailBinding
import com.example.travelpartner.model.CafeModel
import com.example.travelpartner.model.RestaurantModel

class CafeDetailFragment : Fragment() {private lateinit var binding: FragmentCafeDetailBinding
    private val cafeList = mutableListOf<CafeModel>()
    private lateinit var cafeAdapter: CafeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCafeDetailBinding.inflate(layoutInflater)
        cafeAdapter = CafeAdapter(requireContext(), cafeList)
        cafeAdapter = cafeAdapter

        val name = arguments?.getString("name") ?: "No Name"
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        val description = arguments?.getString("bn_desc") ?: "No Description"
        val rating = arguments?.getString("rating") ?: "No Rating"
        val latitude = arguments?.getString("latitude") ?: "No Latitude"
        val longitude = arguments?.getString("longitude") ?: "No Longitude"

        binding.cafeTitle.text = name
        binding.cafeDescription.text = description
        binding.cafeRating.text = rating
        Glide.with(requireContext()).load(imageUrl).into(binding.cafeImage)

        val locationCoordinates = "Lat: $latitude, Long: $longitude"
        binding.btnSeeCafeLocation.text = "See Location"

        binding.btnSeeCafeLocation.setOnClickListener {
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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarCafeDetails)
        binding.toolbarCafeDetails.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}