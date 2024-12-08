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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.travelpartner.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BannerAdapter
    private lateinit var layoutManager: LinearLayoutManager
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

        binding.indicatorTabLayout
        //setupTabIndicators()
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

        binding.btnHotel.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, HotelsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnRestaurant.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, RestaurantFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnCafe.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, CafeFragment())
                .addToBackStack(null)
                .commit()
        }

        //nav_drawer
        val dashboardLayout = binding.root.findViewById<LinearLayout>(R.id.dashboard_Id)
        val contactLayout = binding.root.findViewById<LinearLayout>(R.id.contact_Id)

        dashboardLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, DashboardFragment())
                .addToBackStack(null)
                .commit()
        }

        contactLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, PlacesFragment())
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
            setupTabIndicators()
        }.addOnFailureListener { exception ->
            binding.progressBar.visibility = View.GONE
        }
    }
    /*private fun setupIndicator() {
        val slideCount = adapter.itemCount
        val indicators = Array<ImageView?>(slideCount) { null }
        var layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )


        val selectedIndicatorDrawable = R.drawable.selected_indicator
        val unselectedIndicatorDrawable = R.drawable.unselected_indicator

        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext()).apply {
                setImageResource(R.drawable.unselected_indicator)
                layoutParams = layoutParams
                binding.indicatorTabLayout.addView(this)
            }
        }

        setCurrentIndicator(0, indicators)

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                setCurrentIndicator(positionStart, indicators)
            }
        })
    }

    private fun setCurrentIndicator(position: Int, indicators: Array<ImageView?>) {
        indicators.forEach { it?.setImageResource(R.drawable.unselected_indicator) }
        indicators[position]?.setImageResource(R.drawable.selected_indicator)
    }
*/
    private fun setupTabIndicators() {
        val tabLayout = binding.indicatorTabLayout
        tabLayout.removeAllTabs()

        // Add tabs programmatically
        repeat(adapter.itemCount) { index ->
            val tab = tabLayout.newTab()

            // Set a custom view for each tab (e.g., image or text)
            val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.see_all_places, null)
            val imageView = tabView.findViewById<ImageView>(R.id.place_location)
            // You can set an icon or text for each tab
            imageView.setImageResource(R.drawable.selected_indicator) // Set an appropriate drawable

            tab.customView = tabView // Set custom view for this tab
            tabLayout.addTab(tab) // Add the tab to the TabLayout
        }

        // Optionally set a listener to handle tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.bannerRecyclerView.smoothScrollToPosition(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}
