package com.example.readme.data.remote

//알라딘 API 응답 모델 (예시)
data class AladdinResponse(
    val version: String,
    val title: String,
    val item: List<BookItem>
)

data class BookItem(
    val title: String,
    val author: String,
    val priceStandard: Int,
    val cover: String
)
