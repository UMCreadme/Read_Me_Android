package com.example.readme.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//임시 ApiService
interface ReadmeServerService {
    @POST("yourEndpoint")
    fun postData(@Body data: YourDataClass): Call<ReadmeResponse>

    @GET("yourEndpoint")
    fun getData(): Call<ReadmeResponse>

    companion object {
        //나중에 서버 URL 추가
        const val BASE_URL = "https://your-custom-server.com/api/"
    }
}