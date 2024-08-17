package com.example.readme.ui.community

data class Chat(
    val messageId: Int,
    val userId: Int,
    val nickname: String,
    val content: String,
    val createdAt: String,
    val isMine: Boolean = false
)
