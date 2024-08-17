package com.example.readme.data.remote

data class CommunityMessageResponse(
    val id: String,
    val content: String,
    val timestamp: String
)

data class MessageRequest(
    val content: String
)

data class PostResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String
)
