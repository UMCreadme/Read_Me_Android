package com.example.readme.ui.login

data class KakaoResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: KakaoResult
)

data class KakaoResult(
    val accessToken: String,
    val refreshToken: String
)
