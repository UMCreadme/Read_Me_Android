package com.example.readme.data.entities

import com.example.readme.data.remote.CommunityMessageResponse

data class ChatResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String?,
    val pageInfo: PageInfo,
    val result: List<CommunityMessageResponse>?
)

data class PageInfo(
    val page: Int,
    val size: Int,
    val hasNext: Boolean
)

data class ChatMessage(
    val messageId: Int,
    val userId: Int,
    val nickname: String,
    val content: String,
    val createdAt: String,
    val isMine: Boolean
)
