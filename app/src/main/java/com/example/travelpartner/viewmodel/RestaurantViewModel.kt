package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.RestaurantModel
import com.example.travelpartner.repository.RestaurantRepository

class RestaurantViewModel: ViewModel() {
    private val repository = RestaurantRepository()
    private val _restaurant = MutableLiveData<List<RestaurantModel>>()
    val restaurant: LiveData<List<RestaurantModel>> get() = _restaurant
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchRestaurants()
    }
    private fun fetchRestaurants() {
        _isLoading.value = true
        repository.fetchRestaurants().observeForever { restaurant ->
            _restaurant.value = restaurant
            _isLoading.postValue(false)
        }
    }
}