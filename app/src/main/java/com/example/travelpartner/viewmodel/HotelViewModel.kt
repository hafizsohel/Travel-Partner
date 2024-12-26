package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.HotelModel
import com.example.travelpartner.model.LocationModel
import com.example.travelpartner.repository.HotelRepository

class HotelViewModel : ViewModel() {
    private val repository = HotelRepository()
    private val _hotel = MutableLiveData<List<HotelModel>>()
    val hotel: LiveData<List<HotelModel>> get() = _filteredHotel
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _filteredHotel = MutableLiveData<List<HotelModel>>()

    init {
        fetchHotels()
    }
    private fun fetchHotels() {
        _isLoading.value = true
        repository.fetchHotels().observeForever { hotels ->
            _hotel.value = hotels
            _filteredHotel.value = hotels
            _isLoading.postValue(false)
        }
    }
    fun searchHotel(query: String) {
        if (query.isEmpty()) {
            _filteredHotel.value = _hotel.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredHotel.value = _hotel.value?.filter { resort ->
                resort.name?.contains(query, ignoreCase = true) == true
            }
        }
    }
}