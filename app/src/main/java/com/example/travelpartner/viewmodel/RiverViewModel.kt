package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.RiverModel
import com.example.travelpartner.repository.RiverRepository

class RiverViewModel : ViewModel() {
    private val repository = RiverRepository()
    private val _river = MutableLiveData<List<RiverModel>>()
    val filteredRiver: LiveData<List<RiverModel>> get() = _filteredRiver
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _filteredRiver = MutableLiveData<List<RiverModel>>()

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>> get() = _districts

    init {
        fetchRivers()
    }
    private fun fetchRivers() {
        _isLoading.value = true
        repository.fetchRivers().observeForever { river ->
            _river.value = river
            _filteredRiver.value = river
            _isLoading.postValue(false)
            _districts.value = river.map { it.district }.distinct().sorted()
        }
    }

    fun searchRiver(query: String) {
        if (query.isEmpty()) {
            _filteredRiver.value = _river.value
        } else {
            _filteredRiver.value = _river.value?.filter { river ->
                river.name?.contains(query, ignoreCase = true) == true
            }
        }
    }
    fun filterRiversByDistrict(district: String) {
        _filteredRiver.value = _river.value?.filter { it.district == district }
        }
    }