package com.example.travelpartner.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.example.travelpartner.R
import com.example.travelpartner.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(inflater, container, false)

        // Setup Toolbar
        val toolbar = binding.toolbarContact as Toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Call Button
        binding.buttonCall.setOnClickListener {
            val phoneNumber = "tel:+8801773642161"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
            startActivity(intent)
        }

        // Email Button
        binding.buttonEmail.setOnClickListener {
            val email = "sohel.hafizurrahman@gmail.com"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(intent)
        }

        // Location Button
        binding.buttonLocation.setOnClickListener {
            val locationUri = Uri.parse("geo:23.784109450114283,90.42554424263183?q=23.784109450114283,90.42554424263183(Suffix+IT)")
            val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        return binding.root
    }
}
