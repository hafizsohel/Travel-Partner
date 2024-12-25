package com.example.travelpartner.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelpartner.model.LocationModel
import com.google.firebase.database.*

private const val TAG = "LocationRepository"
class LocationRepository {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("locations")

    fun fetchDestinations(): LiveData<List<LocationModel>> {
        val liveData = MutableLiveData<List<LocationModel>>()
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val destinationList = mutableListOf<LocationModel>()
                snapshot.children.forEach {
                    val destination = it.getValue(LocationModel::class.java)
                    if (destination != null) {
                        destinationList.add(destination)
                    }
                }
                liveData.value = destinationList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return liveData
    }
}