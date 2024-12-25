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
import com.example.travelpartner.adapter.CafeAdapter
import com.example.travelpartner.adapter.RestaurantAdapter
import com.example.travelpartner.databinding.FragmentCafeBinding
import com.example.travelpartner.model.CafeModel
import com.example.travelpartner.model.RestaurantModel
import com.example.travelpartner.utils.GetCafesHelper
import com.example.travelpartner.utils.GetRestaurantsHelper
import com.example.travelpartner.viewmodel.CafeViewModel
import com.example.travelpartner.viewmodel.ResortViewModel

class CafeFragment : Fragment() {
    private lateinit var binding: FragmentCafeBinding
    private lateinit var viewModel: CafeViewModel
    private lateinit var cafeAdapter: CafeAdapter
    private val cafeList = mutableListOf<CafeModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCafeBinding.inflate(inflater, container, false)
        setupToolbar()

        cafeAdapter = CafeAdapter(requireContext(), cafeList)
        binding.cafeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cafeRecyclerView.adapter = cafeAdapter

        viewModel = ViewModelProvider(this)[CafeViewModel::class.java]
       /* viewModel.districts.observe(viewLifecycleOwner) { districtList ->
            setupDistrictDropdown(districtList)
        }*/
        //viewModel.fetchDistricts()


        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            toggleProgressBar(isLoading)
        }
        viewModel.cafe.observe(viewLifecycleOwner) { restaurant ->
            cafeAdapter.updateData(restaurant)
        }

        cafeAdapter.onItemClicked = { selectedLocation ->
            GetCafesHelper.navigateToCafeDetailFragment(
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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarCafe)
        binding.toolbarCafe.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun toggleProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.cafeProgressBar.visibility = View.VISIBLE
            binding.cafeRecyclerView.visibility = View.GONE
        } else {
            binding.cafeProgressBar.visibility = View.GONE
            binding.cafeRecyclerView.visibility = View.VISIBLE
        }
    }
}