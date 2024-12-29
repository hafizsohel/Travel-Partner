package com.example.travelpartner.viewmodel

import com.example.travelpartner.repository.LocationRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.repository.ResortRepository

class LocationViewModel : ViewModel() {
    private val repository = LocationRepository()
    private val _destinations = MutableLiveData<List<LocationModel>>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()
    private val _filteredLocation = MutableLiveData<List<LocationModel>>()
    val filteredLocation: LiveData<List<LocationModel>> get() = _filteredLocation


    //
    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>> get() = _districts


    init {
        fetchDestinations()
    }

    private fun fetchDestinations() {
        _isLoading.value = true
        repository.fetchDestinations().observeForever { destinations ->
            _destinations.value = destinations
            _filteredLocation.value = destinations
            _isLoading.postValue(false)
            //
            _districts.value = destinations.map { it.district }.distinct().sorted()
        }
    }
    //new
    fun filterResortsByDistrict(district: String) {
        _filteredLocation.value = _destinations.value?.filter { it.district == district }
    }

    fun searchLocation(query: String) {
        if (query.isEmpty()) {
            _filteredLocation.value = _destinations.value
        } else {
            _filteredLocation.value = _destinations.value?.filter { destination ->
                destination.name?.contains(query, ignoreCase = true) == true
            }
        }
    }
}