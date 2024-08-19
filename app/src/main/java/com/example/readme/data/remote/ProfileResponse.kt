package com.example.readme.data.remote

data class ProfileResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: ProfileResult
)

data class ProfileResult(
    val userId: Int,
    val nickname: String,
    val account: String,
    val comment: String,
    val readCount: Int,
    val followerNum: Int,
    val followingNum: Int,
    val profileImg: String
)

data class ProfileUpdateRequest(
    val nickname: String?,
    val account: String?,
    val comment: String?
)

