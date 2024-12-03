package com.example.travelpartner.fragment

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelpartner.adapter.BannerAdapter
import com.example.travelpartner.databinding.FragmentDashboardBinding
import com.example.travelpartner.model.Banner
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.travelpartner.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BannerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var handler: Handler
    private lateinit var scrollRunnable: Runnable
    private val banners = mutableListOf<Banner>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.btnMenu.setOnClickListener {
            openDrawer(binding.drawerLayer)
        }

        recyclerView = binding.bannerRecyclerView
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = BannerAdapter(banners)
        recyclerView.adapter = adapter

        fetchBannersFromFirestore()
        binding.progressBar.visibility = View.VISIBLE

        binding.btnPlaces.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, PlacesFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnResort.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, ResortsFragment())
                .addToBackStack(null)
                .commit()
        }
        showSlider()
        return binding.root
    }

    private fun showSlider() {
        viewLifecycleOwner.lifecycleScope.launch {
           while(true) {
                val position = layoutManager.findFirstVisibleItemPosition()
                if (position < adapter.itemCount - 1) {
                    recyclerView.smoothScrollToPosition(position + 1)
                } else {
                    recyclerView.smoothScrollToPosition(0)
                }
                delay(5000)
            }
        }
    }

    private fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun fetchBannersFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Locations").get().addOnSuccessListener { result ->
            banners.clear()
            for (document in result) {
                val banner = document.toObject(Banner::class.java)
                banners.add(banner)
            }
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener { exception ->
            binding.progressBar.visibility = View.GONE
        }
    }
}
