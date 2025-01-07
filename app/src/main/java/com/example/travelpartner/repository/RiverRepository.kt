package com.example.travelpartner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelpartner.model.CafeModel
import com.example.travelpartner.model.RiverModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RiverRepository {

    private val databaseRef = FirebaseDatabase.getInstance().getReference("rivers")

    fun fetchRivers(): LiveData<List<RiverModel>> {
        val liveData = MutableLiveData<List<RiverModel>>()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cafeList = mutableListOf<RiverModel>()
                snapshot.children.forEach {
                    val river = it.getValue(RiverModel::class.java)
                    if (river != null) {
                        cafeList.add(river)
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