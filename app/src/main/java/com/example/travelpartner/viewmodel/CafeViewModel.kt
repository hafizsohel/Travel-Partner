package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.CafeModel
import com.example.travelpartner.repository.CafeRepository

class CafeViewModel : ViewModel() {
    private val repository = CafeRepository()
    private val _cafe = MutableLiveData<List<CafeModel>>()
    val filteredCafe: LiveData<List<CafeModel>> get() = _filteredCafe
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _filteredCafe = MutableLiveData<List<CafeModel>>()

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>> get() = _districts

    init {
        fetchCafes()
    }
    private fun fetchCafes() {
        _isLoading.value = true
        repository.fetchCafes().observeForever { cafe ->
            _cafe.value = cafe
            _filteredCafe.value = cafe
            _isLoading.postValue(false)
            _districts.value = cafe.map { it.district }.distinct().sorted()
        }
    }

    fun searchCafe(query: String) {
        if (query.isEmpty()) {
            _filteredCafe.value = _cafe.value
        } else {
            _filteredCafe.value = _cafe.value?.filter { resort ->
                resort.name?.contains(query, ignoreCase = true) == true
            }
        }
    }
    fun filterCafesByDistrict(district: String) {
        _filteredCafe.value = _cafe.value?.filter { it.district == district }
    }
}