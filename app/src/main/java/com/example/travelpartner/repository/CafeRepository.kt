package com.example.travelpartner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelpartner.model.CafeModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CafeRepository {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("cafes")

    fun fetchCafes(): LiveData<List<CafeModel>> {
        val liveData = MutableLiveData<List<CafeModel>>()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cafeList = mutableListOf<CafeModel>()
                snapshot.children.forEach {
                    val cafe = it.getValue(CafeModel::class.java)
                    if (cafe != null) {
                        cafeList.add(cafe)
                    }
                }
                liveData.value = cafeList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return liveData
    }
}