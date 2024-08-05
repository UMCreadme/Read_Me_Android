package com.example.readme.utils

import com.example.readme.data.remote.AladdinService

import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.login.KakaoLoginService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var aladdinRetrofit: Retrofit? = null
    private var kakaoRetrofit: Retrofit? = null
    private var customRetrofit: Retrofit? = null

    // 알라딘 API Retrofit 객체 생성
    fun getAladdinService(): AladdinService {
        if (aladdinRetrofit == null) {
            aladdinRetrofit = Retrofit.Builder()
                .baseUrl(AladdinService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return aladdinRetrofit!!.create(AladdinService::class.java)
    }

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
        if (customRetrofit == null) {
            customRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return customRetrofit!!.create(ReadmeServerService::class.java)
    }
}
