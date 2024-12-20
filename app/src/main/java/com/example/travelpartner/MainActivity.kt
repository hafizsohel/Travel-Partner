package com.example.travelpartner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.travelpartner.databinding.ActivityMainBinding
import com.example.travelpartner.fragment.DashboardFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.charcoal)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.FrameLayoutID, DashboardFragment())
                .commit()
        }
    }
}