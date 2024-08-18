package com.example.readme.utils

import android.util.Log
import com.example.readme.data.remote.*
import com.example.readme.data.repository.SearchRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.readme.data.remote.AladdinService
import com.example.readme.data.remote.KakaoLoginService
import com.example.readme.data.remote.ReadmeServerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var kakaoRetrofit: Retrofit? = null
    private var readmeRetrofit: Retrofit? = null
    private var locationRetrofit: Retrofit? = null
    private var chatRetrofit: Retrofit? = null
    private var mainInfoRetrofit: Retrofit? = null
    private var token: String? = null

    fun setToken(accessToken: String) {
        this.token = accessToken
        Log.d("Retrofit", "accessToken 전달 완료 : $token")

        // 토큰이 변경되었으므로, 기존의 Retrofit 인스턴스를 무효화
        readmeRetrofit = null
        mainInfoRetrofit = null
        locationRetrofit = null
        chatRetrofit = null

        SearchRepository.init(getReadmeServerService())
    }

    // 로깅 인터셉터
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // auth 인터셉터
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer $token")
            .method(original.method, original.body)
            .build()

        Log.d("RetrofitClient", "Request to ${request.url} with token: ${token}")


        chain.proceed(request)
    }

    // OkHttpClient 객체 생성
    private val client: OkHttpClient
        get() = if (token != null) {
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(interceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

    // 카카오톡 로그인 API Retrofit 객체 생성
    fun getKakaoLoginService(): KakaoLoginService {
        if (kakaoRetrofit == null) {
            kakaoRetrofit = Retrofit.Builder()
                .baseUrl(KakaoLoginService.BASE_URL)
                .client(client) // 클라이언트 추가
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return kakaoRetrofit!!.create(KakaoLoginService::class.java)
    }

    // MainInfoService Retrofit 객체 생성
    fun getMainInfoService(): MainInfoService {
        if (mainInfoRetrofit == null) {
            mainInfoRetrofit = Retrofit.Builder()
                .baseUrl(MainInfoService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return mainInfoRetrofit!!.create(MainInfoService::class.java)
    }

    // Location 서버 API Retrofit 객체 생성
    fun getLocationService(): LocationService {
        if (locationRetrofit == null) {
            locationRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return locationRetrofit!!.create(LocationService::class.java)
    }

    // ChatFetchService Retrofit 객체 생성
    fun getChatFetchService(): ChatFetchService {
        if (chatRetrofit == null) {
            chatRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return chatRetrofit!!.create(ChatFetchService::class.java)
    }

    // Readme 서버 API Retrofit 객체 생성
    fun getReadmeServerService(): ReadmeServerService {
        if (readmeRetrofit == null) {
            readmeRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return readmeRetrofit!!.create(ReadmeServerService::class.java)
    }
}
