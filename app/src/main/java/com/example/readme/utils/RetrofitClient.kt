package com.example.readme.utils

import com.example.readme.data.remote.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object RetrofitClient {

    // Retrofit 인스턴스를 여러 개 만들기 위해 nullable로 선언된 변수들
    private var kakaoRetrofit: Retrofit? = null
    private var readmeRetrofit: Retrofit? = null
    private var locationRetrofit: Retrofit? = null
    private var chatRetrofit: Retrofit? = null
    private var mainInfoRetrofit: Retrofit? = null
    private var createRetrofit: Retrofit? = null  // 추가된 변수

    // 토큰 (옵션에 따라 authInterceptor에서 사용)
    private val token: String? = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozLCJlbWFpbCI6InJlYWRtZV9hZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTcyMzk2Mzc4NCwiZXhwIjoxNzI1MTczMzg0fQ.KKAWxunZaf2g_8yek3TE08kZFHKjOio0UFj2K3LbrUM"

    // 로깅 인터셉터: 요청/응답을 로그로 출력합니다.
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Auth 인터셉터: 요청에 인증 헤더를 추가합니다.
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer ${token ?: ""}") // 토큰이 null일 경우 빈 문자열로 대체
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    // SSLContext 및 TrustManager 설정
    private val trustManager: X509TrustManager = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm()
    ).apply {
        init(null as KeyStore?)
    }.trustManagers[0] as X509TrustManager

    private val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf(trustManager), null)
    }

    // OkHttpClient 객체 생성: 인증이 필요한 경우와 아닌 경우로 분기 처리
    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        if (token != null) {
            addInterceptor(authInterceptor)
        }
        addInterceptor(interceptor)

        // SSL 설정 추가
        sslSocketFactory(sslContext.socketFactory, trustManager)
        hostnameVerifier { _, _ -> true } // 필요에 따라 호스트 이름 검증을 끔
    }.build()

    // Retrofit 객체를 생성하는 공통 함수
    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 카카오톡 로그인 API Retrofit 객체 생성
    fun getKakaoLoginService(): KakaoLoginService {
        if (kakaoRetrofit == null) {
            kakaoRetrofit = createRetrofitInstance(KakaoLoginService.BASE_URL)
        }
        return kakaoRetrofit!!.create(KakaoLoginService::class.java)
    }

    // MainInfoService Retrofit 객체 생성
    fun getMainInfoService(): MainInfoService {
        if (mainInfoRetrofit == null) {
            mainInfoRetrofit = createRetrofitInstance(MainInfoService.BASE_URL)
        }
        return mainInfoRetrofit!!.create(MainInfoService::class.java)
    }

    // Location 서버 API Retrofit 객체 생성
    fun getLocationService(): LocationService {
        if (locationRetrofit == null) {
            locationRetrofit = createRetrofitInstance(ReadmeServerService.BASE_URL)
        }
        return locationRetrofit!!.create(LocationService::class.java)
    }

    // ChatFetchService Retrofit 객체 생성
    fun getChatFetchService(): ChatFetchService {
        if (chatRetrofit == null) {
            chatRetrofit = createRetrofitInstance(ReadmeServerService.BASE_URL)
        }
        return chatRetrofit!!.create(ChatFetchService::class.java)
    }

    // Readme 서버 API Retrofit 객체 생성
    fun getReadmeServerService(): ReadmeServerService {
        if (readmeRetrofit == null) {
            readmeRetrofit = createRetrofitInstance(ReadmeServerService.BASE_URL)
        }
        return readmeRetrofit!!.create(ReadmeServerService::class.java)
    }

    // Single instance의 ReadmeServerService 사용을 위한 lazy 초기화
    val apiService: ReadmeServerService by lazy {
        getReadmeServerService()
    }

    // CreateService Retrofit 객체 생성
    fun getCreateService(): CreateService {
        if (createRetrofit == null) {
            createRetrofit = createRetrofitInstance(ReadmeServerService.BASE_URL)
        }
        return createRetrofit!!.create(CreateService::class.java)
    }
}