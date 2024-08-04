package com.example.readme.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//임시 ApiService
interface ApiService {
    @POST("path/to/your/api")
    fun sendData(@Body dataList: List<YourDataClass>): Call<YourResponse>
}