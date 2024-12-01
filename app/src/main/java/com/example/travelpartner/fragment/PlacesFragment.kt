package com.example.travelpartner.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.adapter.Location
import com.example.travelpartner.adapter.LocationAdapter
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.google.firebase.database.*

private const val TAG = "PlacesFragment"

class PlacesFragment : Fragment() {

    private lateinit var binding: FragmentPlacesBinding
    private lateinit var database: DatabaseReference
    private lateinit var locationAdapter: LocationAdapter
    private val locationList = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("divisions")

        fetchDivisionNames()

        // Set up the RecyclerView
        setupRecyclerView()

        // Handle Search Button Click
        binding.btnSearch.setOnClickListener {
            val selectedDivision = binding.autoCompleteDivision.text.toString()
            val selectedDistrict = binding.autoCompleteDistrict.text.toString()
            val selectedUpazila = binding.autoCompleteUpazila.text.toString()
            val selectedUnion = binding.autoCompleteUnion.text.toString()

            // For now, just log the selected values
            Log.d(TAG, "Selected Division: $selectedDivision")
            Log.d(TAG, "Selected District: $selectedDistrict")
            Log.d(TAG, "Selected Upazila: $selectedUpazila")
            Log.d(TAG, "Selected Union: $selectedUnion")

            // You can modify the code here to filter results based on selections
            // For example, fetch data based on selectedDivision, selectedDistrict, etc.
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        locationAdapter = LocationAdapter(locationList)
        binding.recyclerViewPlaces.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationAdapter
        }
    }

    private fun fetchDivisionNames() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val divisionNames = mutableListOf<String>()
                for (divisionSnapshot in snapshot.children) {
                    val divisionName = divisionSnapshot.child("name").getValue(String::class.java)
                    divisionName?.let { divisionNames.add(it) }
                }

                if (divisionNames.isNotEmpty()) {
                    Log.d(TAG, "Division Names: $divisionNames")
                    populateSpinner(divisionNames)
                } else {
                    //showError("No divisions found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //showError("Error fetching divisions: ${error.message}")
            }
        })
    }

    private fun populateSpinner(divisionNames: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            divisionNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.autoCompleteDivision.setAdapter(adapter)
    }
}

