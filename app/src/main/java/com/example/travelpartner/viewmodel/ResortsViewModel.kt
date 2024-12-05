package com.example.travelpartner.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class ResortsViewModel : ViewModel() {

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>> get() = _districts

    fun fetchDistricts() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("districts")
        databaseReference.get().addOnSuccessListener { snapshot ->
            val districtList = snapshot.children.mapNotNull {
                it.child("bn_name").value as? String
            }
            _districts.value = districtList
        }.addOnFailureListener { exception ->
            Log.e("ResortsViewModel", "Error fetching districts", exception)
        }
    }
}
