package com.example.readme.ui.community

data class PostData(
    val ISBN: String,
    val content: String,
    val tags: String,
    val location: String,
    val capacity: Int,
    val userId: Int
)