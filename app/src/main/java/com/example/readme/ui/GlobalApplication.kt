package com.example.readme.ui

import android.app.Application
import com.example.readme.data.repository.SearchRepository
import com.example.readme.utils.RetrofitClient
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "dbe31c51bcd2bae3fbab42016106e896")

        // 초기화 시 RetrofitClient 설정
        val apiService = RetrofitClient.getReadmeServerService()
        SearchRepository.init(apiService)
    }
}