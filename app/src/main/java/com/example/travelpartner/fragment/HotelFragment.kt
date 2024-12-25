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
import com.example.travelpartner.adapter.HotelAdapter
import com.example.travelpartner.databinding.FragmentHotelsBinding
import com.example.travelpartner.model.HotelModel
import com.example.travelpartner.utils.GetHotelsHelper
import com.example.travelpartner.viewmodel.HotelViewModel

class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var viewModel: HotelViewModel
    private lateinit var hotelAdapter: HotelAdapter
    private val hotelList = mutableListOf<HotelModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelsBinding.inflate(inflater, container, false)
        setupToolbar()
        viewModel = ViewModelProvider(this)[HotelViewModel::class.java]

        hotelAdapter = HotelAdapter(requireContext(), hotelList)
        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.hotelRecyclerView.adapter = hotelAdapter


        viewModel = ViewModelProvider(this)[HotelViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            toggleProgressBar(isLoading)
        }
        viewModel.hotel.observe(viewLifecycleOwner) { hotel ->
            hotelAdapter.updateData(hotel)
        }

        hotelAdapter.onItemClicked = { selectedLocation ->
            GetHotelsHelper.navigateToHotelDetailFragment(parentFragmentManager, selectedLocation)
        }



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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarHotels)
        binding.toolbarHotels.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun toggleProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.hotelProgressBar.visibility = View.VISIBLE
            binding.hotelRecyclerView.visibility = View.GONE
        } else {
            binding.hotelProgressBar.visibility = View.GONE
            binding.hotelRecyclerView.visibility = View.VISIBLE
        }
    }
}