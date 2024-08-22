package com.example.travelpartner.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.example.travelpartner.adapter.BannerAdapter
import com.example.travelpartner.databinding.FragmentDashboardBinding
import com.example.travelpartner.model.Banner
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewPager: ViewPager
    private lateinit var bannerAdapter: BannerAdapter
    private val bannerList = mutableListOf<Banner>()
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        binding.btnMenu.setOnClickListener {
            openDrawer(binding.drawerLayer)
        }

        viewPager = binding.viewPager
        bannerAdapter = BannerAdapter(requireContext(), bannerList)
        viewPager.adapter = bannerAdapter

        database = FirebaseDatabase.getInstance().getReference("locations/Bandorbon")
        loadBannerData()

        return binding.root
    }

    private fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun loadBannerData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bannerList.clear()
                for (dataSnapshot in snapshot.children) {
                    val banner = dataSnapshot.getValue(Banner::class.java)
                    if (banner != null) {
                        bannerList.add(banner)
                    }
                }
                bannerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
