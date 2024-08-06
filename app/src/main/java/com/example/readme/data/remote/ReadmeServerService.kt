package com.example.readme.data.remote

import com.example.readme.ui.community.Chat
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//임시 ApiService
interface ReadmeServerService {
    @GET("community/{communityId}/messages")
    fun getMessages(@Path("communityId") communityId: String): Call<List<Chat>>

    @POST("community/{communityId}/messages")
    fun postMessage(@Path("communityId") communityId: String, @Body chat: Chat): Call<Chat>

    companion object {
        const val BASE_URL = "https://your.api.base.url/"
    }
}