package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travelpartner.databinding.FragmentRestaurantBinding
import com.example.travelpartner.viewmodel.ResortViewModel

class RestaurantFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var viewModel: ResortViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        setupToolbar()
        viewModel = ViewModelProvider(this)[ResortViewModel::class.java]
       /* viewModel.districts.observe(viewLifecycleOwner) { districtList ->
            setupDistrictDropdown(districtList)
        }*/
       // viewModel.fetchDistricts()
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
}