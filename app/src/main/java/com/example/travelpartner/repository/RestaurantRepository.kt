package com.example.travelpartner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelpartner.model.RestaurantModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RestaurantRepository {
    private val databaseRef = FirebaseDatabase.getInstance().getReference("restaurants")

    fun fetchRestaurants(): LiveData<List<RestaurantModel>> {
        val liveData = MutableLiveData<List<RestaurantModel>>()

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val restaurantList = mutableListOf<RestaurantModel>()
                snapshot.children.forEach {
                    val restaurant = it.getValue(RestaurantModel::class.java)
                    if (restaurant != null) {
                        restaurantList.add(restaurant)
                    }
                }
                liveData.value = restaurantList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return liveData
    }
}