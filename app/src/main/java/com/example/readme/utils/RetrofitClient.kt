package com.example.readme.utils

import com.example.readme.data.remote.AladdinService
import com.example.readme.data.remote.ChatFetchService
import com.example.readme.data.remote.KakaoLoginService
import com.example.readme.data.remote.LocationService
import com.example.readme.data.remote.ReadmeServerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var aladdinRetrofit: Retrofit? = null
    private var kakaoRetrofit: Retrofit? = null
    private var readmeRetrofit: Retrofit? = null
    private var locationRetrofit: Retrofit? = null
    private var chatRetrofit: Retrofit? = null // 추가된 Retrofit 인스턴스

    private const val BASE_URL = "https://api.umcreadme11.shop/" // 기본 URL

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
        if (readmeRetrofit == null) {
            readmeRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return readmeRetrofit!!.create(ReadmeServerService::class.java)
    }

    // Location 서버 API Retrofit 객체 생성
    fun getLocationService(): LocationService {
        if (locationRetrofit == null) {
            locationRetrofit = Retrofit.Builder()
                .baseUrl("https://api.umcreadme11.shop/") // 실제 서버 URL로 변경하세요.
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return locationRetrofit!!.create(LocationService::class.java)
    }

    // ChatFetchService Retrofit 객체 생성
    fun getChatFetchService(): ChatFetchService {
        if (chatRetrofit == null) {
            chatRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) // BASE_URL을 사용
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return chatRetrofit!!.create(ChatFetchService::class.java)
    }
}
