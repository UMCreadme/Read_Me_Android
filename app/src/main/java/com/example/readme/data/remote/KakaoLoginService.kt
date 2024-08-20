package com.example.readme.data.remote

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface KakaoLoginService {
    @POST("v2/user/me")
    fun getUserInfo(@Header("Authorization") accessToken: String): Call<KakaoResponse>

    companion object {
        const val BASE_URL = "https://kapi.kakao.com/"
    }
}