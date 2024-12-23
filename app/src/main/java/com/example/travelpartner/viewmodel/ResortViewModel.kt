package com.example.travelpartner.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.repository.LocationRepository
import com.example.travelpartner.repository.ResortRepository
import com.google.firebase.database.FirebaseDatabase

class ResortViewModel : ViewModel() {
    private val repository = ResortRepository()
    private val _resort = MutableLiveData<List<ResortModel>>()
    val resort: LiveData<List<ResortModel>> get() = _resort

    init {
        fetchResorts()
    }

    private fun fetchResorts() {
        repository.fetchResorts().observeForever { destinations ->
            _resort.value = destinations
        }
    }
}
