package com.example.readme.ui.community

data class Chat(
    val messageId: Int,
    val userId: Int,
    val content: String,
    val createdAt: String,
    val isSelf: Boolean
)