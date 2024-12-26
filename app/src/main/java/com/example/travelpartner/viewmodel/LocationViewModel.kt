package com.example.travelpartner.viewmodel

import com.example.travelpartner.repository.LocationRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.model.ResortModel

class LocationViewModel : ViewModel() {
    private val repository = LocationRepository()
    private val _destinations = MutableLiveData<List<LocationModel>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    val destinations: LiveData<List<LocationModel>> get() = _filteredLocation
    private val _filteredLocation = MutableLiveData<List<LocationModel>>()

    init {
        fetchDestinations()
    }

    private fun fetchDestinations() {
        _isLoading.value = true
        repository.fetchDestinations().observeForever { destinations ->
            _destinations.value = destinations
            _filteredLocation.value = destinations
            _isLoading.postValue(false)
        }
    }

    fun searchLocation(query: String) {
        if (query.isEmpty()) {
            _filteredLocation.value = _destinations.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredLocation.value = _destinations.value?.filter { resort ->
                resort.name?.contains(query, ignoreCase = true) == true
            }
        }
    }

}
