package com.example.travelpartner.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.adapter.DestinationAdapter
import com.example.travelpartner.adapter.LocationAdapter
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.example.travelpartner.utils.GetLocationsHelper
import com.example.travelpartner.viewmodel.LocationViewModel

class LocationFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var adapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(layoutInflater)

        adapter = LocationAdapter(requireContext(), mutableListOf())
        binding.placeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.placeRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            toggleProgressBar(isLoading)
        }

        viewModel.destinations.observe(viewLifecycleOwner) { destinations ->
            adapter.updateData(destinations)

        }

        adapter.onItemClicked = { selectedLocation ->
            GetLocationsHelper.navigateToLocationDetailFragment(parentFragmentManager, selectedLocation)
        }
        setupSearch()
        setupToolbar()
        return binding.root
    }

    private fun setupSearch() {
        binding.searchLocations.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val query = charSequence.toString().trim()
                viewModel.searchLocation(query)
            }
            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarPlace)
        binding.toolbarPlace.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun toggleProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.locationProgressBar.visibility = View.VISIBLE
            binding.placeRecyclerView.visibility = View.GONE
        } else {
            binding.locationProgressBar.visibility = View.GONE
            binding.placeRecyclerView.visibility = View.VISIBLE
        }
    }

}

