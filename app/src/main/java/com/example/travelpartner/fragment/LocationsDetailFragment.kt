package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelpartner.adapter.DestinationAdapter
import com.example.travelpartner.databinding.FragmentLocationsDetailBinding
import com.example.travelpartner.model.DestinationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LocationsDetailFragment : Fragment() {
    private lateinit var binding:FragmentLocationsDetailBinding
    private val destinationList = mutableListOf<DestinationModel>()
    private lateinit var destinationAdapter: DestinationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=FragmentLocationsDetailBinding.inflate(layoutInflater)

        destinationAdapter= DestinationAdapter(requireContext(), destinationList)
        setupToolbar()
        fetchLocationDetails()
        return binding.root
    }
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarDetails)
        binding.toolbarDetails.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }
    private fun fetchLocationDetails(){
        val databaseReference = FirebaseDatabase.getInstance().getReference("locations")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                destinationList.clear()
                for (dataSnapshot in snapshot.children) {
                    val place = dataSnapshot.getValue(DestinationModel::class.java)
                    place?.let { destinationList.add(it) }
                }
                destinationAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}