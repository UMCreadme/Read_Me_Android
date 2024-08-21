package com.example.readme.data.remote

data class CommunityMessageResponse(
    val id: Int = 134,
    val content: String? = null,
    val timestamp: String? = null
)

data class MessageRequest(
    val content: String
)

data class PostResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String?
)
