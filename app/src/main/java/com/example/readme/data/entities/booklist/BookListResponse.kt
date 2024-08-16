package com.example.readme.data.entities.booklist

import com.example.readme.data.remote.PageInfo
import com.google.gson.annotations.SerializedName

// 전체 응답을 감싸는 데이터 클래스
data class BookListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: List<Book>
)

// 책 데이터 클래스
data class Book(
    @SerializedName("ISBN") val isbn: String,
    @SerializedName("bookCover") val bookCover: String,
    @SerializedName("bookTitle") val bookTitle: String,
    @SerializedName("author") val author: String
)