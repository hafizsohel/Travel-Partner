package com.example.travelpartner.viewmodel

import com.example.travelpartner.repository.LocationRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.LocationModel
class LocationViewModel : ViewModel() {
        private val repository = LocationRepository()
        private val _destinations = MutableLiveData<List<LocationModel>>()
        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean> get() = _isLoading
        val destinations: LiveData<List<LocationModel>> get() = _destinations

        init {
            fetchDestinations()
        }

        private fun fetchDestinations() {
            _isLoading.value = true
            repository.fetchDestinations().observeForever { destinations ->
                _destinations.value = destinations
                _isLoading.postValue(false)
            }
        }
    }
