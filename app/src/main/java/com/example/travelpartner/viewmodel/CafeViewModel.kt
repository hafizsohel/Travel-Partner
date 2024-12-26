package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.CafeModel
import com.example.travelpartner.model.RestaurantModel
import com.example.travelpartner.repository.CafeRepository

class CafeViewModel : ViewModel() {
    private val repository = CafeRepository()
    private val _cafe = MutableLiveData<List<CafeModel>>()
    val cafe: LiveData<List<CafeModel>> get() = _filteredCafe
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _filteredCafe = MutableLiveData<List<CafeModel>>()

    init {
        fetchCafes()
    }
    private fun fetchCafes() {
        _isLoading.value = true
        repository.fetchCafes().observeForever { cafe ->
            _cafe.value = cafe
            _filteredCafe.value = cafe
            _isLoading.postValue(false)
        }
    }

    fun searchCafe(query: String) {
        if (query.isEmpty()) {
            _filteredCafe.value = _cafe.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredCafe.value = _cafe.value?.filter { resort ->
                resort.name?.contains(query, ignoreCase = true) == true
            }
        }
    }
}