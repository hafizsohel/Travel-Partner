package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.R
import com.example.travelpartner.adapter.OptionListsAdapter
import com.example.travelpartner.databinding.FragmentOthersBinding

class OthersFragment : Fragment() {
    private lateinit var binding:FragmentOthersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOthersBinding.inflate(layoutInflater)

        binding.recyclerViewOptions.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }
}