package com.example.readme.data.remote

//임시 ApiResponse
//1. 응답 중 데이터를 주고 받을 때 사용하는 데이터 클래스 (필드 - 값)이 받아오는 구조라고 보면 됩니다.

//나중에 데이터 클래스 세분화 필요함
data class YourDataClass(
    val field1: String,
    val field2: Int,
    val field3: Boolean
)

//2. API 응답의 성공 여부나 메세지를 처리하는 데이터 클래스
data class ReadmeResponse(
    val isSuccess: Boolean,
    val message: String
)

data class BasicResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String
)

