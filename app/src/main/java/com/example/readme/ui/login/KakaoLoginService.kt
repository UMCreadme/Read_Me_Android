// KakaoLoginService.kt
package com.example.readme.ui.login

import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.entities.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoLoginService {
    @POST("users/login")
    suspend fun sendKakaoUserInfo(@Body user: KaKaoUser): Response<LoginResponse>

    @POST("users/sign")
    suspend fun sendSignUpInfo(@Body user: UserData): Response<LoginResponse>

    companion object {
        const val BASE_URL = "https://api.umcreadme11.shop/"
    }
}
