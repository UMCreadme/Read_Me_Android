package com.example.readme.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AladdinService {
    @GET("ItemSearch.aspx")
    fun searchBooks(
        @Query("ttbkey") ttbKey: String,
        @Query("Query") query: String,
        @Query("output") output: String
    ): Call<AladdinResponse>

    companion object {
        const val BASE_URL = "https://www.aladin.co.kr/ttb/api/"
    }
}
