package com.example.readme.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommunityService {
    @GET("community/{id}/details")
    fun getCommunityDetail(@Path("id") communityId: Int): Call<CommunityDetailResponse>
}
