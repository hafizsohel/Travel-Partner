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
import com.example.travelpartner.adapter.ResortAdapter
import com.example.travelpartner.databinding.FragmentResortsBinding
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.utils.GetLocationsHelper
import com.example.travelpartner.viewmodel.ResortsViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private const val TAG = "ResortsFragment"
class ResortsFragment : Fragment() {
    private lateinit var binding: FragmentResortsBinding
    private lateinit var viewModel: ResortsViewModel
    private lateinit var resortAdapter: ResortAdapter
    private val resortList = mutableListOf<ResortModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResortsBinding.inflate(inflater, container, false)

        // Initialize the adapter
        resortAdapter = ResortAdapter(requireContext(), resortList)
        binding.resortRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resortAdapter
        }

        resortAdapter.onItemClicked = { selectedResort ->
            val bundle = Bundle().apply {

            }
            /*   findNavController().navigate(R.id.action_resortListFragment_to_resortDetailsFragment, bundle)
        }

        destinationAdapter.onItemClicked = { selectedLocation ->
            GetLocationsHelper.navigateToLocationDetailFragment(parentFragmentManager, selectedLocation)
        }*/
        }

        setupToolbar()
        viewModel = ViewModelProvider(this)[ResortsViewModel::class.java]
        viewModel.districts.observe(viewLifecycleOwner) { districtList ->
            setupDistrictDropdown(districtList)
        }
        viewModel.fetchDistricts()

        FetchAllResorts()
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
    private fun FetchAllResorts() {
        // Fetch data from Firebase
        binding.resortProgressBar.visibility = View.VISIBLE
        val databaseReference = FirebaseDatabase.getInstance().getReference("resorts")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                resortList.clear()
                for (dataSnapshot in snapshot.children) {
                    val place = dataSnapshot.getValue(ResortModel::class.java)
                    place?.let { resortList.add(it) }
                }
                binding.resortProgressBar.visibility = View.GONE
                resortAdapter.notifyDataSetChanged()

                Log.d(TAG, "onDataChange: $databaseReference")
            }

            override fun onCancelled(error: DatabaseError) {
                binding.resortProgressBar.visibility = View.GONE
                Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
