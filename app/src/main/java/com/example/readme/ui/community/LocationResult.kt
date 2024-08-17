package com.example.readme.ui.community


data class LocationResult (
    val latitude: Double,
    val longitude: Double,
    val formatted_address: String,
    val geometry: Geometry
)