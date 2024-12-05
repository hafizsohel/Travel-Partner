package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.travelpartner.R
import com.example.travelpartner.databinding.FragmentCafeBinding
import com.example.travelpartner.databinding.FragmentRestaurantBinding
import com.example.travelpartner.viewmodel.ResortsViewModel

class CafeFragment : Fragment() {
    private lateinit var binding: FragmentCafeBinding
    private lateinit var viewModel: ResortsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCafeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ResortsViewModel::class.java]
        viewModel.districts.observe(viewLifecycleOwner) { districtList ->
            setupDistrictDropdown(districtList)
        }
        viewModel.fetchDistricts()
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
}