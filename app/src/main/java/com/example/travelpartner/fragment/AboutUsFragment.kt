package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.travelpartner.R
import com.example.travelpartner.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {
    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(layoutInflater)

        val toolbar = binding.toolbarAbout as Toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }
}