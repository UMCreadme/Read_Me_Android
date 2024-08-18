package com.example.readme.data.remote

import com.example.readme.data.entities.ChatResponse
import com.example.readme.ui.community.Chat
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatFetchService {
    @GET("communities/{communityId}/messages")
    suspend fun fetchMessages(@Path("communityId") communityId: String): Response<ChatResponse>


    @POST("communities/{communityId}/messages")
    suspend fun sendMessage(
        @Path("communityId") communityId: String,
        @Body messageRequest: MessageRequest): Response<CommunityMessageResponse>
}
