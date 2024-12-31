package com.example.travelpartner.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
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
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelpartner.R
import com.example.travelpartner.adapter.DestinationAdapter
import com.example.travelpartner.adapter.OptionListsAdapter
import com.example.travelpartner.utils.GetLocationsHelper
import com.example.travelpartner.application.GridSpacingItemDecoration
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.viewmodel.NoticeViewModel
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
    private val destinationList = mutableListOf<LocationModel>()
    private lateinit var noticeBar: TextView
    private lateinit var noticeScrollView: HorizontalScrollView
    private lateinit var noticeViewModel: NoticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.btnMenu.setOnClickListener {
            openDrawer(binding.drawerLayer)
        }
        noticeBar = binding.noticeBar
        noticeScrollView = binding.noticeScrollView
        recyclerView = binding.bannerRecyclerView
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = layoutManager
        adapter = BannerAdapter(banners)

        destinationAdapter = DestinationAdapter(requireContext(), destinationList)
        binding.destinationRecyclerView.adapter = destinationAdapter
        recyclerView.adapter = adapter

        val padding = resources.getDimensionPixelSize(R.dimen.item_padding)
        binding.destinationRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.destinationRecyclerView.addItemDecoration(GridSpacingItemDecoration(padding))

        setupDotsIndicator()
        fetchBannersFromFirestore()
        FetchAllLocation()
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar1.visibility = View.VISIBLE
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager


        binding.btnPlaces.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, LocationFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnResort.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, ResortFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnHotel.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, HotelFragment())
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

        binding.btnSeeAll.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, SeeLocationFragment())
                .addToBackStack(null)
                .commit()
        }

        //nav_drawer
        val dashboardLayout = binding.root.findViewById<LinearLayout>(R.id.dashboard_Id)
        val contactLayout = binding.root.findViewById<LinearLayout>(R.id.contact_Id)
        val aboutLayout = binding.root.findViewById<LinearLayout>(R.id.about_Us)

        dashboardLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, DashboardFragment())
                .addToBackStack(null)
                .commit()
        }

        contactLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, ContactFragment())
                .addToBackStack(null)
                .commit()
        }

        aboutLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FrameLayoutID, AboutUsFragment())
                .addToBackStack(null)
                .commit()
        }

        showSlider()
        duplicateTextContent()
        noticeScrollView.post { scrollNoticeBar() }

        destinationAdapter.onItemClicked = { selectedLocation ->
            GetLocationsHelper.navigateToLocationDetailFragment(parentFragmentManager, selectedLocation)
        }
        noticeViewModel = ViewModelProvider(this)[NoticeViewModel::class.java]
        noticeViewModel.notice.observe(viewLifecycleOwner) { notice ->
            binding.noticeBar.text = notice
        }

        return binding.root
    }

    private fun showSlider() {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
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
    }

    private fun FetchAllLocation() {
        // Fetch data from Firebase
        binding.progressBar1.visibility = View.VISIBLE
        val databaseReference = FirebaseDatabase.getInstance().getReference("locations")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                destinationList.clear()
                for (dataSnapshot in snapshot.children) {
                    val place = dataSnapshot.getValue(LocationModel::class.java)
                    place?.let { destinationList.add(it) }
                }
                binding.progressBar1.visibility = View.GONE
                destinationAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar1.visibility = View.GONE
                Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun duplicateTextContent() {
        val textContent = noticeBar.text.toString()
        noticeBar.text = "$textContent    $textContent"
    }

    private fun scrollNoticeBar() {
        val scrollWidth = noticeBar.width / 2

        if (scrollWidth > 0) {
            val animator = ObjectAnimator.ofInt(
                noticeScrollView,
                "scrollX",
                0,
                scrollWidth
            ).apply {
                duration = 15000
                repeatCount = ObjectAnimator.INFINITE
                interpolator = null
                addUpdateListener {
                    if (noticeScrollView.scrollX >= scrollWidth) {
                        noticeScrollView.scrollTo(0, 0)
                    }
                }
                start()
            }
        } else {
            noticeScrollView.postDelayed({ scrollNoticeBar() }, 100)
        }
    }
}

