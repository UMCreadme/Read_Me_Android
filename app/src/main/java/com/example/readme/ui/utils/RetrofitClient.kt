package com.example.readme.ui.utils

import android.webkit.HttpAuthHandler
import com.example.readme.data.remote.AladdinService
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.ui.data.remote.MainInfoService
import com.example.readme.ui.data.remote.ShortsPostService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var aladdinRetrofit: Retrofit? = null
    private var kakaoRetrofit: Retrofit? = null
    private var customRetrofit: Retrofit? = null
    private var mainInfoRetrofit: Retrofit? = null
    private var shortspostRetrofit : Retrofit ?= null
    private var token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozLCJlbWFpbCI6InJlYWRtZV9hZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcyMzk4OTI1NywiZXhwIjoxNzI1MTk4ODU3fQ.43PpymU8MtuWnbN0GErib6IRW73Mu9sT2pmGJyXI8UE"

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

    fun getShortsPostService(): ShortsPostService {
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
        if (shortspostRetrofit == null) {
            shortspostRetrofit = Retrofit.Builder()
                .baseUrl(ShortsPostService.BASE_URL)  // baseUrl은 해당 서비스에 맞게 설정
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return shortspostRetrofit!!.create(ShortsPostService::class.java)
    }








//    // 카카오톡 로그인 API Retrofit 객체 생성
//    fun getKakaoLoginService(): KakaoLoginService {
//        if (kakaoRetrofit == null) {
//            kakaoRetrofit = Retrofit.Builder()
//                .baseUrl(KakaoLoginService.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return kakaoRetrofit!!.create(KakaoLoginService::class.java)
//    }

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