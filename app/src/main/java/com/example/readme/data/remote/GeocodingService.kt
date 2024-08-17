package com.example.readme.data.remote

import com.example.community.data.remote.GeocodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("geocode/json")
    fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Call<GeocodingResponse>
}
//
//data class GeocodingResponse(val results: List<Result>)
//data class Result(val formatted_address: String, val geometry: Geometry)
//data class Geometry(val location: Location)
//data class Location(val lat: Double, val lng: Double)