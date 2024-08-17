package com.example.readme.data.remote

import com.example.readme.ui.community.LocationData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// LocationService 인터페이스 정의
interface LocationService {

    @POST("location") // 서버의 엔드포인트를 "location"으로 가정
    fun sendLocation(@Body locationData: LocationData): Call<Void>

}
