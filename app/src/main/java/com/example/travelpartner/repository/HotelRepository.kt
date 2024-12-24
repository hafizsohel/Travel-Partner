package com.example.travelpartner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelpartner.model.HotelModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HotelRepository {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("hotels")

    fun fetchHotels(): LiveData<List<HotelModel>> {
        val liveData = MutableLiveData<List<HotelModel>>()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hotelList = mutableListOf<HotelModel>()
                snapshot.children.forEach {
                    val hotel = it.getValue(HotelModel::class.java)
                    if (hotel != null) {
                        hotelList.add(hotel)
                    }
                }
                liveData.value = hotelList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return liveData
    }
}