package com.example.travelpartner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelpartner.databinding.FragmentResortsBinding
import com.example.travelpartner.viewmodel.ResortsViewModel

class ResortsFragment : Fragment() {
    private lateinit var binding: FragmentResortsBinding
    private lateinit var viewModel: ResortsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResortsBinding.inflate(inflater, container, false)

        setupToolbar()
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
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarResort)
        binding.toolbarResort.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
