package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.RestaurantModel
import com.example.travelpartner.repository.RestaurantRepository

class RestaurantViewModel: ViewModel() {
    private val repository = RestaurantRepository()
    private val _restaurant = MutableLiveData<List<RestaurantModel>>()
    val restaurant: LiveData<List<RestaurantModel>> get() = _filteredRestaurant
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _filteredRestaurant = MutableLiveData<List<RestaurantModel>>()

    init {
        fetchRestaurants()
    }
    private fun fetchRestaurants() {
        _isLoading.value = true
        repository.fetchRestaurants().observeForever { restaurant ->
            _restaurant.value = restaurant
            _filteredRestaurant.value = restaurant
            _isLoading.postValue(false)
        }
    }
    fun searchRestaurant(query: String) {
        if (query.isEmpty()) {
            _filteredRestaurant.value = _restaurant.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredRestaurant.value = _restaurant.value?.filter { resort ->
                resort.name?.contains(query, ignoreCase = true) == true
            }
        }
    }
}