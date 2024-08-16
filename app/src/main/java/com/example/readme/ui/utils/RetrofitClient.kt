package com.example.readme.ui.utils

import com.example.readme.data.remote.KakaoLoginService
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.data.remote.MainInfoService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var kakaoRetrofit: Retrofit? = null
    private var ReadmeRetrofit: Retrofit? = null
    private var mainInfoRetrofit: Retrofit? = null
    private val token: String? = null

    // MainInfoService Retrofit 객체 생성
    fun getMainInfoService(): MainInfoService {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor{ chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(interceptor)
            .build()
        if (mainInfoRetrofit == null) {
            mainInfoRetrofit = Retrofit.Builder()
                .baseUrl(MainInfoService.BASE_URL)  // baseUrl은 해당 서비스에 맞게 설정
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return mainInfoRetrofit!!.create(MainInfoService::class.java)
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
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

        val client = if(token !== null) {
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(interceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        if (ReadmeRetrofit == null) {
            ReadmeRetrofit = Retrofit.Builder()
                .baseUrl(ReadmeServerService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return ReadmeRetrofit!!.create(ReadmeServerService::class.java)
    }
}
