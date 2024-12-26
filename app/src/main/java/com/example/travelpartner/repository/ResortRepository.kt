package com.example.travelpartner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelpartner.model.ResortModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResortRepository {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("resorts")

    fun fetchResorts(): LiveData<List<ResortModel>> {
        val liveData = MutableLiveData<List<ResortModel>>()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val resortList = mutableListOf<ResortModel>()
                snapshot.children.forEach {
                    val resort = it.getValue(ResortModel::class.java)
                    if (resort != null) {
                        resortList.add(resort)
                    }
                }
                liveData.value = resortList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return liveData
    }
}