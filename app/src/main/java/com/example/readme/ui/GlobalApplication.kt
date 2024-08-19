package com.example.readme.ui

import android.app.Application
import com.example.readme.BuildConfig
import com.example.readme.data.repository.SearchRepository
import com.example.readme.utils.RetrofitClient
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // 초기화 시 RetrofitClient 설정
        val apiService = RetrofitClient.getReadmeServerService()
        SearchRepository.init(apiService)
    }
}