package com.example.readme.data.remote

data class MyPageResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: MyPageResult
)

data class MyPageResult(
    val userId: Int,
    val nickname: String,
    val account: String,
    val comment: String,
    val email: String,
    val followerNum: Int,
    val followingNum: Int,
    val profileImg: String,
    val isRecentPost: Boolean,
    val readCount: Int
)