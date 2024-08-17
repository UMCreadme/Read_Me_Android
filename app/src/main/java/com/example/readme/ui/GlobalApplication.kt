package com.example.readme.ui

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
//        KakaoSdk.init(this, "dbe31c51bcd2bae3fbab42016106e896")
        KakaoSdk.init(this, "8bd1ca39d5eb15687ae52deb301f1abe")
        Log.e("hashKey", Utility.getKeyHash(this))

    }
}