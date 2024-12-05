package com.example.travelpartner.fragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.adapter.LocationAdapter
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.viewmodel.LocationViewModel
import com.example.travelpartner.viewmodel.PlacesViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val TAG = "PlacesFragment"
class PlacesFragment : Fragment() {

    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: PlacesViewModel
    private lateinit var adapter: LocationAdapter
    private lateinit var locationViewModel: LocationViewModel
    private var selectedUnion: String = "1084"

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("locations")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

        setupToolbar()
        setupObservers()
        viewModel.fetchDivisions()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.divisions.observe(viewLifecycleOwner) { divisions ->
            setupDropdown(binding.autoCompleteDivision, divisions.map { it.bn_name }) { selected ->
                val divisionId = divisions.find { it.bn_name == selected }?.id.orEmpty()
                binding.autoCompleteDistrict.setText("")
                binding.autoCompleteUpazila.setText("")
                binding.autoCompleteUnion.setText("")
                viewModel.fetchDistricts(divisionId)
            }
        }

        viewModel.districts.observe(viewLifecycleOwner) { districts ->
            setupDropdown(binding.autoCompleteDistrict, districts.map { it.bn_name }) { selected ->
                val districtId = districts.find { it.bn_name == selected }?.id.orEmpty()
                binding.autoCompleteUpazila.setText("")
                binding.autoCompleteUnion.setText("")
                viewModel.fetchUpazilas(districtId)
            }
        }

        viewModel.upazilas.observe(viewLifecycleOwner) { upazilas ->
            setupDropdown(binding.autoCompleteUpazila, upazilas.map { it.bn_name }) { selected ->
                val upazilaId = upazilas.find { it.bn_name == selected }?.id.orEmpty()
                binding.autoCompleteUnion.setText("")
                viewModel.fetchUnions(upazilaId)
            }
        }

        viewModel.unions.observe(viewLifecycleOwner) { unions ->
            setupDropdown(binding.autoCompleteUnion, unions.map { it.bn_name }) { selected ->
                Log.d(TAG, "Selected Union: $selected")
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewPlaces.layoutManager = LinearLayoutManager(requireContext())
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        locationViewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            if (locations.isNotEmpty()) {
                adapter = LocationAdapter(locations)
                binding.recyclerViewPlaces.adapter = adapter
                Log.d(TAG, "Observed locations: $locations")
            } else {
                Toast.makeText(requireContext(), "No locations found", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSearch.setOnClickListener {
            Log.d(TAG, "Button clicked, selectedUnion: $selectedUnion") // Log selectedUnion here
            locationViewModel.fetchLocations()
            Log.d(TAG, "fetchLocations called with selectedUnion: $selectedUnion")
        }

    }

    private fun setupDropdown(
        dropdown: AutoCompleteTextView,
        items: List<String>,
        onItemSelected: (String) -> Unit
    ) {
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        dropdown.setAdapter(adapter)
        dropdown.setOnItemClickListener { _, _, position, _ ->
            onItemSelected(items[position])
        }
    }
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarPlaces)
        binding.toolbarPlaces.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}

