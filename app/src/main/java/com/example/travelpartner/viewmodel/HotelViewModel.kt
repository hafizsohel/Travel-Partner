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

    init {
        fetchHotels()
    }
    private fun fetchHotels() {
        repository.fetchHotels().observeForever { hotels ->
            _hotel.value = hotels
        }
    }
}