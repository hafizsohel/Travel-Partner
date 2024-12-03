package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.repository.LocationRepository
class LocationViewModel : ViewModel() {

    private val repository = LocationRepository()
    private val _locations = MutableLiveData<List<LocationModel>>()
    val locations: LiveData<List<LocationModel>> get() = _locations

    fun fetchLocations() {
        repository.fetchLocations { locationList ->
            _locations.value = locationList
        }
    }
}

