package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.adapter.RestaurantAdapter
import com.example.travelpartner.databinding.FragmentRestaurantBinding
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.model.RestaurantModel
import com.example.travelpartner.utils.GetRestaurantsHelper
import com.example.travelpartner.viewmodel.ResortViewModel
import com.example.travelpartner.viewmodel.RestaurantViewModel

class RestaurantFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var restaurantAdapter: RestaurantAdapter
    private val restaurantList = mutableListOf<RestaurantModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantBinding.inflate(layoutInflater)
        setupToolbar()
        viewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]
        /* viewModel.districts.observe(viewLifecycleOwner) { districtList ->
             setupDistrictDropdown(districtList)
         }*/
        // viewModel.fetchDistricts()
        restaurantAdapter = RestaurantAdapter(requireContext(), restaurantList)
        binding.restaurantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.restaurantRecyclerView.adapter = restaurantAdapter

        viewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            toggleProgressBar(isLoading)
        }
        viewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
            restaurantAdapter.updateData(restaurant)
        }

        restaurantAdapter.onItemClicked = { selectedLocation ->
            GetRestaurantsHelper.navigateToRestaurantDetailFragment(
                parentFragmentManager,
                selectedLocation
            )
        }
        return binding.root
    }

    private fun setupDistrictDropdown(districts: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            districts
        )
        binding.autoCompleteDistrict.setAdapter(adapter)
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarRestaurant)
        binding.toolbarRestaurant.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun toggleProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.restaurantProgressBar.visibility = View.VISIBLE
            binding.restaurantRecyclerView.visibility = View.GONE
        } else {
            binding.restaurantProgressBar.visibility = View.GONE
            binding.restaurantRecyclerView.visibility = View.VISIBLE
        }
    }
}