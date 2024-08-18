package com.example.readme.data.entities

data class User(
    val userId: Int,
    val nickname: String,
    val account: String,
    val comment: String,
    val readCount: Int,
    val followerNum: Int,
    val followingNum: Int,
    val profileImg: String
)