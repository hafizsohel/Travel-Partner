package com.example.travelpartner.repository

import android.util.Log
import com.example.travelpartner.model.LocationModel
import com.google.firebase.database.*

private const val TAG = "LocationRepository"
class LocationRepository {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("locations")

    fun fetchLocations(callback: (List<LocationModel>) -> Unit) {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val locationList = mutableListOf<LocationModel>()

                Log.d(TAG, "onDataChange: Snapshot: $snapshot")

                for (locationSnapshot in snapshot.children) {
                    Log.d(TAG, "onDataChange: Location child snapshot: $locationSnapshot")

                    val location = locationSnapshot.getValue(LocationModel::class.java)
                    if (location != null) {
                        locationList.add(location)
                        Log.d(TAG, "onDataChange: Location: $location")
                    }
                }
                callback(locationList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching data: ${error.message}")
            }
        })
    }
}