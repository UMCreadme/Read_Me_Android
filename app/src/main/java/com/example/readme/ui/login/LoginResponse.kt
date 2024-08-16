package com.example.readme.ui.login

data class LoginResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ResultData?
)

data class ResultData(
    val accessToken: String,
    val refreshToken: String
)