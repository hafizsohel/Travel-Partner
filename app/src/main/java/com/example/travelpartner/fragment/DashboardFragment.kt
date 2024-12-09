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
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelpartner.R
import com.example.travelpartner.adapter.DestinationAdapter
import com.example.travelpartner.model.DestinationModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BannerAdapter
    private lateinit var destinationAdapter: DestinationAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val banners = mutableListOf<Banner>()
    private val destinationList = mutableListOf<DestinationModel>()


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

      // destinationAdapter = DestinationAdapter(context, destinationList = mutableListOf())

        destinationAdapter= DestinationAdapter(requireContext(), destinationList)
        binding.destinationRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.destinationRecyclerView.adapter = destinationAdapter



        recyclerView.adapter = adapter
        setupDotsIndicator()
        fetchBannersFromFirestore()
        binding.progressBar.visibility = View.VISIBLE
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager

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
            setupDotsIndicator()
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener { exception ->
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupDotsIndicator() {
        val dotsLayout = binding.indicatorLayout
        val dotCount = adapter.itemCount

        dotsLayout.removeAllViews()
        for (i in 0 until dotCount) {
            val dot = ImageView(requireContext()).apply {
                setImageResource(if (i == 0) R.drawable.selected_indicator else R.drawable.unselected_indicator)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(8, 0, 8, 0) }
                layoutParams = params
            }
            dotsLayout.addView(dot)
        }

        binding.bannerRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val currentPosition = layoutManager.findFirstVisibleItemPosition()
                for (i in 0 until dotsLayout.childCount) {
                    val dot = dotsLayout.getChildAt(i) as ImageView
                    dot.setImageResource(if (i == currentPosition) R.drawable.selected_indicator else R.drawable.unselected_indicator)
                }
            }
        })
        // Fetch data from Firebase
        val databaseReference = FirebaseDatabase.getInstance().getReference("locations")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                destinationList.clear()
                for (dataSnapshot in snapshot.children) {
                    val place = dataSnapshot.getValue(DestinationModel::class.java)
                    place?.let { destinationList.add(it) }
                }
                destinationAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }
    }

