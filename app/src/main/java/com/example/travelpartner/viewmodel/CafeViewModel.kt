package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.CafeModel
import com.example.travelpartner.repository.CafeRepository

class CafeViewModel : ViewModel() {
    private val repository = CafeRepository()
    private val _cafe = MutableLiveData<List<CafeModel>>()
    val cafe: LiveData<List<CafeModel>> get() = _cafe
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchCafes()
    }
    private fun fetchCafes() {
        _isLoading.value = true
        repository.fetchCafes().observeForever { cafe ->
            _cafe.value = cafe
            _isLoading.postValue(false)
        }
    }
}