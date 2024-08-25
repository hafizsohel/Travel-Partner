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
import androidx.recyclerview.widget.RecyclerView



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
        handler = Handler(Looper.getMainLooper())
        scrollRunnable = object : Runnable {
            override fun run() {
                val position = layoutManager.findFirstVisibleItemPosition()
                if (position < adapter.itemCount - 1) {
                    recyclerView.smoothScrollToPosition(position + 1)
                } else {
                    recyclerView.smoothScrollToPosition(0)
                }
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(scrollRunnable, 3000)
        return binding.root
    }

    private fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }
    private fun fetchBannersFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Locations").get().addOnSuccessListener { result ->
            for (document in result) {
                val banner = document.toObject(Banner::class.java)
                banners.add(banner)
            }
            adapter.notifyDataSetChanged()


        }.addOnFailureListener { exception ->
            Log.w("DashboardFragment", "Error getting documents: ", exception)
        }
    }
}
