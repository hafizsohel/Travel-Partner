package com.example.travelpartner.fragment

import android.os.Bundle
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
        viewModel.destinations.observe(viewLifecycleOwner) { destinations ->
            adapter.updateData(destinations)
        }

        adapter.onItemClicked = { selectedLocation ->
            GetLocationsHelper.navigateToLocationDetailFragment(parentFragmentManager, selectedLocation)
        }

        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarPlace)
        binding.toolbarPlace.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}

