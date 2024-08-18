package com.example.readme.data.remote

import com.example.readme.ui.community.PostData
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateService {
    @POST("communities")
    suspend fun createPost(@Body postData: PostData): Response<PostResponse>
}
