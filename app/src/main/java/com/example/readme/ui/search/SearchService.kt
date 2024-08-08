package com.example.readme.ui.search

import com.example.readme.data.entities.RecentSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface SearchService {
    @GET("/recent-searches")
    fun getRecentSearches(
        @Header("Authorization") BaeerToken: String
    ): Call<RecentSearchResponse>

    companion object {
        const val BASE_URL = "https://api.umcreadme11.shop/"
    }
}