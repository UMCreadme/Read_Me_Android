// LoginRepository.kt
package com.example.readme.ui.login

import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.entities.UserData

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginRepository {

    private val retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // OkHttpClient 설정에 로깅 인터셉터 추가
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)  // 로깅 인터셉터 추가
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()


        retrofit = Retrofit.Builder()
            .baseUrl("http://api.umcreadme11.shop/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val service = retrofit.create(KakaoLoginService::class.java)

    suspend fun sendKakaoUserInfo(user: KaKaoUser): Response<LoginResponse> {
        return service.sendKakaoUserInfo(user)
    }

    suspend fun sendSignUpInfo(user: UserData): Response<LoginResponse> {
        return service.sendSignUpInfo(user)
    }
}
