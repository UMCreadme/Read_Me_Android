package com.example.readme.data.remote

import com.example.readme.data.entities.RecentSearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

//임시 ApiService
interface ReadmeServerService {
    @POST("yourEndpoint")
    fun postData(@Body data: YourDataClass): Call<ReadmeResponse>

    @GET("yourEndpoint")
    fun getData(): Call<ReadmeResponse>

    @GET("/recent-searches")
    suspend fun getRecentSearches(
        @Header("Authorization") token: String
    ): RecentSearchResponse

    companion object {

        //나중에 서버 URL 추가
        const val BASE_URL ="https://api.umcreadme11.shop/"
    }
}