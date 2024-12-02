package com.example.travelpartner.fragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.example.travelpartner.viewmodel.PlacesViewModel

private const val TAG = "PlacesFragment"
class PlacesFragment : Fragment() {

    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: PlacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

        setupToolbar()
        setupObservers()
        setupListeners()
        viewModel.fetchDivisions()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.divisions.observe(viewLifecycleOwner) { divisions ->
            setupDropdown(binding.autoCompleteDivision, divisions.map { it.bn_name }) { selected ->
                val divisionId = divisions.find { it.bn_name == selected }?.id.orEmpty()
                binding.autoCompleteDistrict.setText("")
                binding.autoCompleteUpazila.setText("")
                binding.autoCompleteUnion.setText("")
                viewModel.fetchDistricts(divisionId)
            }
        }

        viewModel.districts.observe(viewLifecycleOwner) { districts ->
            setupDropdown(binding.autoCompleteDistrict, districts.map { it.bn_name }) { selected ->
                val districtId = districts.find { it.bn_name == selected }?.id.orEmpty()
                binding.autoCompleteUpazila.setText("")
                binding.autoCompleteUnion.setText("")
                viewModel.fetchUpazilas(districtId)
            }
        }

        viewModel.upazilas.observe(viewLifecycleOwner) { upazilas ->
            setupDropdown(binding.autoCompleteUpazila, upazilas.map { it.bn_name }) { selected ->
                val upazilaId = upazilas.find { it.bn_name == selected }?.id.orEmpty()
                binding.autoCompleteUnion.setText("")
                viewModel.fetchUnions(upazilaId)
            }
        }

        viewModel.unions.observe(viewLifecycleOwner) { unions ->
            setupDropdown(binding.autoCompleteUnion, unions.map { it.bn_name }) { selected ->
                Log.d(TAG, "Selected Union: $selected")
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDropdown(
        dropdown: AutoCompleteTextView,
        items: List<String>,
        onItemSelected: (String) -> Unit
    ) {
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, items)
        dropdown.setAdapter(adapter)
        dropdown.setOnItemClickListener { _, _, position, _ ->
            onItemSelected(items[position])
        }
    }

    private fun setupListeners() {
        binding.btnSearch.setOnClickListener {

        }
    }
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarPlaces)
        binding.toolbarPlaces.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}

