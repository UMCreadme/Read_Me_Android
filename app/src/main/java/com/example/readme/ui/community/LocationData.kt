package com.example.readme.ui.community

// 데이터 클래스 정의 (서버로 전송할 위치 정보)
data class LocationData(
    val address: String,
    val latitude: Double,
    val longitude: Double
)
