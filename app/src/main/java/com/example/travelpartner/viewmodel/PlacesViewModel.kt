package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelpartner.model.District
import com.example.travelpartner.model.Division
import com.example.travelpartner.model.Union
import com.example.travelpartner.model.Upazila
import com.google.firebase.database.*

class PlacesViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().reference

    private val _divisions = MutableLiveData<List<Division>>()
    val divisions: LiveData<List<Division>> get() = _divisions

    private val _districts = MutableLiveData<List<District>>()
    val districts: LiveData<List<District>> get() = _districts

    private val _upazilas = MutableLiveData<List<Upazila>>()
    val upazilas: LiveData<List<Upazila>> get() = _upazilas

    private val _unions = MutableLiveData<List<Union>>()
    val unions: LiveData<List<Union>> get() = _unions

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchDivisions() {
        database.child("divisions").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull {
                    Division(it.key.orEmpty(), it.child("bn_name").getValue(String::class.java).orEmpty())
                }
                _divisions.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                _error.value = "Error fetching divisions: ${error.message}"
            }
        })
    }

    fun fetchDistricts(divisionId: String) {
        database.child("districts").orderByChild("division_id").equalTo(divisionId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        District(it.key.orEmpty(), it.child("bn_name").getValue(String::class.java).orEmpty())
                    }
                    _districts.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = "Error fetching districts: ${error.message}"
                }
            })
    }

    fun fetchUpazilas(districtId: String) {
        database.child("upazilas").orderByChild("district_id").equalTo(districtId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        Upazila(it.key.orEmpty(), it.child("bn_name").getValue(String::class.java).orEmpty())
                    }
                    _upazilas.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = "Error fetching upazilas: ${error.message}"
                }
            })
    }

    fun fetchUnions(upazilaId: String) {
        database.child("unions").orderByChild("upazilla_id").equalTo(upazilaId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        Union(it.key.orEmpty(), it.child("bn_name").getValue(String::class.java).orEmpty())
                    }
                    _unions.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = "Error fetching unions: ${error.message}"
                }
            })
    }
    fun fetchLocations(unionId: String) {
        database.child("locations").orderByChild("union_id").equalTo(unionId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        Union(it.key.orEmpty(), it.child("bn_name").getValue(String::class.java).orEmpty())
                    }
                    _unions.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = "Error fetching locations: ${error.message}"
                }
            })
    }
}
