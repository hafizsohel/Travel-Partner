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
    private val _filteredResorts = MutableLiveData<List<ResortModel>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val resort: LiveData<List<ResortModel>> get() = _filteredResorts
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchResorts()
    }

    private fun fetchResorts() {
        _isLoading.value = true
        repository.fetchResorts().observeForever { resorts ->
            _resort.value = resorts
            _filteredResorts.value = resorts
            _isLoading.value = false
        }
    }

    fun searchResort(query: String) {
        if (query.isEmpty()) {
            _filteredResorts.value = _resort.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredResorts.value = _resort.value?.filter { resort ->
                resort.name.contains(query, ignoreCase = true)
            }
        }
    }
}

