package com.example.readme.utils

import CommentListService
import android.util.Log
import com.example.readme.data.remote.*
import com.example.readme.data.repository.SearchRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.data.remote.ShortsPostService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {

    private var retrofitMap: MutableMap<String, Retrofit> = mutableMapOf()
    private var token: String? = null

    fun setToken(accessToken: String) {
        this.token = accessToken
        Log.d("Retrofit", "accessToken 전달 완료 : $token")

        // 토큰이 변경되었으므로, 모든 Retrofit 인스턴스를 무효화
        retrofitMap.clear()

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
        get() = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            token?.let { addInterceptor(authInterceptor) }
        }.build()

    private fun getRetrofit(baseUrl: String): Retrofit {
        return retrofitMap.getOrPut(baseUrl) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    // 카카오톡 로그인 API Retrofit 객체 생성
    fun getKakaoLoginService(): KakaoLoginService {
        return getRetrofit(KakaoLoginService.BASE_URL).create(KakaoLoginService::class.java)
    }

    // MainInfoService Retrofit 객체 생성
    fun getMainInfoService(): MainInfoService {
        return getRetrofit(MainInfoService.BASE_URL).create(MainInfoService::class.java)
    }

    fun getShortsPostService(): ShortsPostService {
        return getRetrofit(ShortsPostService.BASE_URL).create(ShortsPostService::class.java)
    }

    // Location 서버 API Retrofit 객체 생성
    fun getLocationService(): LocationService {
        return getRetrofit(ReadmeServerService.BASE_URL).create(LocationService::class.java)
    }

    // ChatFetchService Retrofit 객체 생성
    fun getChatFetchService(): ChatFetchService {
        return getRetrofit(ReadmeServerService.BASE_URL).create(ChatFetchService::class.java)
    }

    // Readme 서버 API Retrofit 객체 생성
    fun getReadmeServerService(): ReadmeServerService {
        return getRetrofit(ReadmeServerService.BASE_URL).create(ReadmeServerService::class.java)
    }

    // CommentListService Retrofit 객체 생성
    fun getCommentListService(): CommentListService {
        return getRetrofit(ReadmeServerService.BASE_URL).create(CommentListService::class.java)
    }

}
