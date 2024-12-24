package com.example.travelpartner.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.adapter.LocationAdapter
import com.example.travelpartner.adapter.ResortAdapter
import com.example.travelpartner.databinding.FragmentResortsBinding
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.utils.GetLocationsHelper
import com.example.travelpartner.utils.GetResortsHelper
import com.example.travelpartner.viewmodel.LocationViewModel
import com.example.travelpartner.viewmodel.ResortViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private const val TAG = "ResortsFragment"
class ResortFragment : Fragment() {
    private lateinit var binding: FragmentResortsBinding
    private lateinit var viewModel: ResortViewModel
    private lateinit var resortAdapter: ResortAdapter
    private val resortList = mutableListOf<ResortModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResortsBinding.inflate(layoutInflater)

        resortAdapter = ResortAdapter(requireContext(), resortList)
        binding.resortRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.resortRecyclerView.adapter = resortAdapter

        viewModel = ViewModelProvider(this)[ResortViewModel::class.java]
        viewModel.resort.observe(viewLifecycleOwner) { resort ->
            resortAdapter.updateData(resort)
        }

        resortAdapter.onItemClicked = { selectedLocation ->
            GetResortsHelper.navigateToResortDetailFragment(parentFragmentManager, selectedLocation)
            Log.d(TAG, "onCreateView: $resortList")
        }

        setupToolbar()
       /* viewModel = ViewModelProvider(this)[ResortViewModel::class.java]
        viewModel.districts.observe(viewLifecycleOwner) { districtList ->
            setupDistrictDropdown(districtList)
        }
        viewModel.fetchDistricts()*/

       // FetchAllResorts()
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