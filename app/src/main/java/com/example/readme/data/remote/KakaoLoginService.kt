package com.example.readme.data.remote

import com.example.readme.BuildConfig
import com.example.readme.data.LoginResult
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.entities.UserData
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoLoginService {
    @POST("users/login")
    suspend fun sendKakaoUserInfo(@Body user: KaKaoUser): ResponseWithData<LoginResult>

    @POST("users/sign")
    suspend fun sendSignUpInfo(@Body user: UserData): ResponseWithData<LoginResult?>

    companion object {
        const val BASE_URL = BuildConfig.SERVER_URL
    }
}