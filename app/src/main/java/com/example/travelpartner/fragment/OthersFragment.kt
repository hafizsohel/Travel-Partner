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
    private lateinit var optionListsAdapter: OptionListsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOthersBinding.inflate(layoutInflater)


        val options = listOf("River", "Bil", "Forest", "Mountain")

        // RecyclerView setup
        val adapter = OptionListsAdapter(options) { selectedOption ->
            navigateToOptionsFragment(selectedOption)
        }
        binding.recyclerViewOptions.adapter = adapter
        binding.recyclerViewOptions.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }
    private fun navigateToOptionsFragment(option: String) {
        val fragment = when (option) {
            "River" -> RiverFragment()
            "Bil" -> RiverFragment.newInstance("Bil", "ExtraDataForBil")
            else -> RiverFragment.newInstance(option, "DefaultExtraData")
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.FrameLayoutID, fragment)
            .addToBackStack(null)
            .commit()
    }
}