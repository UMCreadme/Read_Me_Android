package com.example.readme.data.entities

data class UserinfoResponse(
    val isSuccess: Boolean,
    val code : String,
    val message : String,
    val result : UserInfoResultData
)

data class UserInfoResultData(
    val accessToken : String,
    val refreshToken : String
)
