package com.example.travelpartner.model

data class Division(
    val id: String, val bn_name: String
)

data class District(
    val id: String, val bn_name: String
)

data class Upazila(
    val id: String, val bn_name: String
)

data class Union(
    val id: String, val bn_name: String
)

data class Location(
    val id:String, val bn_name: String,
    val img:String
)

