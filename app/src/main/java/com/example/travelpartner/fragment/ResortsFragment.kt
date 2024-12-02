package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.travelpartner.R
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.example.travelpartner.databinding.FragmentResortsBinding

private lateinit var binding: FragmentResortsBinding
class ResortsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResortsBinding.inflate(inflater, container, false)

        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarResort)
        binding.toolbarResort.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}