package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.HotelModel
import com.example.travelpartner.repository.HotelRepository

class HotelViewModel : ViewModel() {
    private val repository = HotelRepository()
    private val _hotel = MutableLiveData<List<HotelModel>>()
    val hotel: LiveData<List<HotelModel>> get() = _hotel
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchHotels()
    }
    private fun fetchHotels() {
        _isLoading.value = true
        repository.fetchHotels().observeForever { hotels ->
            _hotel.value = hotels
            _isLoading.postValue(false)
        }
    }
}