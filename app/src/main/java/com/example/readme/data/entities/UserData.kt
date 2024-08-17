package com.example.readme.data.entities

import java.io.Serializable

data class UserData(
    val uniqueId: String,
    val email: String,
    val nickname: String,
    val account: String,
    val categoryIdList: List<Int>
): Serializable
