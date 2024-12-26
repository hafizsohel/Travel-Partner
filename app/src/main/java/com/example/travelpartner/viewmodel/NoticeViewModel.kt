package com.example.travelpartner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class NoticeViewModel : ViewModel() {
    private val _notice = MutableLiveData<String>()
    val notice: LiveData<String> get() = _notice
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("notice")

    init {
        fetchNotice()
    }

    private fun fetchNotice() {
        databaseReference.child("0").child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        _notice.postValue(
                            snapshot.getValue(String::class.java) ?: "No notice available"
                        )
                    } else {
                        _notice.postValue("No notice available")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    _notice.postValue("Error fetching notice: ${error.message}")
            }
        })
    }
}
