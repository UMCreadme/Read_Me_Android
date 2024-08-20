package com.example.readme.data.entities.recentbook

import com.example.readme.data.remote.PageInfo
import com.google.gson.annotations.SerializedName
//쇼츠 생성 시 최근 검색한 책 조회
data class RecentBookResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: List<Book>
)

data class Book(
    @SerializedName("book_id") val bookId: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String
)
