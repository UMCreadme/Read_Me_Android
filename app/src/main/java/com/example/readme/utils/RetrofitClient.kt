package com.example.readme.utils

import com.example.readme.data.remote.AladdinService
import com.example.readme.data.remote.KakaoLoginService
import com.example.readme.data.remote.ReadmeServerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var kakaoRetrofit: Retrofit? = null
    private var ReadmeRetrofit: Retrofit? = null

    // 카카오톡 로그인 API Retrofit 객체 생성
    fun getKakaoLoginService(): KakaoLoginService {
        if (kakaoRetrofit == null) {
            kakaoRetrofit = Retrofit.Builder()
                .baseUrl(KakaoLoginService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return kakaoRetrofit!!.create(KakaoLoginService::class.java)
    }

    // Readme 서버 API Retrofit 객체 생성
    fun getReadmeServerService(): ReadmeServerService {
        if (ReadmeRetrofit == null) {
            ReadmeRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return ReadmeRetrofit!!.create(ReadmeServerService::class.java)
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ReadmeServerService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ReadmeServerService by lazy {
        retrofit.create(ReadmeServerService::class.java)
    }
}
