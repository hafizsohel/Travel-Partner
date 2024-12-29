/*
package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.repository.ResortRepository

class ResortViewModel : ViewModel() {
    private val repository = ResortRepository()
    private val _resort = MutableLiveData<List<ResortModel>>()
    private val _filteredResorts = MutableLiveData<List<ResortModel>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val resort: LiveData<List<ResortModel>> get() = _filteredResorts
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchResorts()
    }

    private fun fetchResorts() {
        _isLoading.value = true
        repository.fetchResorts().observeForever { resorts ->
            _resort.value = resorts
            _filteredResorts.value = resorts
            _isLoading.value = false
        }
    }

    fun searchResort(query: String) {
        if (query.isEmpty()) {
            _filteredResorts.value = _resort.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredResorts.value = _resort.value?.filter { resort ->
                resort.name.contains(query, ignoreCase = true)
            }
        }
    }
}
*/

package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.ResortModel
import com.example.travelpartner.repository.ResortRepository

class ResortViewModel : ViewModel() {
    private val repository = ResortRepository()
    private val _resorts = MutableLiveData<List<ResortModel>>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()
    private val _filteredResorts = MutableLiveData<List<ResortModel>>()
    val filteredResorts: LiveData<List<ResortModel>> get() = _filteredResorts

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>> get() = _districts


    init {
        fetchResorts()
    }

    private fun fetchResorts() {
        _isLoading.value = true
        repository.fetchResorts().observeForever { resorts ->
            _resorts.value = resorts
            _filteredResorts.value = resorts
            _districts.value = resorts.map { it.district }.distinct().sorted()
            _isLoading.value = false
        }
    }

    fun filterResortsByDistrict(district: String) {
        _filteredResorts.value = _resorts.value?.filter { it.district == district }
    }
    fun searchResort(query: String) {
        if (query.isEmpty()) {
            _filteredResorts.value = _resorts.value // Show all resorts if search query is empty
        } else {
            // Filter only by name
            _filteredResorts.value = _resorts.value?.filter { resort ->
                resort.name.contains(query, ignoreCase = true)
            }
        }
    }
}
