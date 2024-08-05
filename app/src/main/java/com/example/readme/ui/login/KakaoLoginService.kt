package com.example.readme.ui.login

import com.example.readme.data.entities.KaKaoUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
//interface KakaoLoginService {
//    @POST("v2/user/me")
//    fun getUserInfo(@Header("Authorization") accessToken: String): Call<KakaoResponse>
//
//    companion object {
//        const val BASE_URL = "https://kapi.kakao.com/"
//    }
//}
interface KakaoLoginService {
    @POST("users/login")
    suspend fun sendKakaoUserInfo(@Body user: KaKaoUser): KakaoResponse

        companion object {
        const val BASE_URL = "https://dev.umcreadme11.shop/"
    }
}
