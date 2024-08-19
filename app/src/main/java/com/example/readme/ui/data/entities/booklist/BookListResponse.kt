package com.example.readme.ui.data.entities.booklist

import com.example.readme.ui.data.entities.recentbook.Book
import com.example.readme.ui.data.entities.recentbook.PageInfo
import com.google.gson.annotations.SerializedName

// 전체 응답을 감싸는 데이터 클래스
data class BookListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: List<com.example.readme.ui.data.entities.booklist.Book>
)

// 페이지 정보 데이터 클래스
data class PageInfo(
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("hasNext") val hasNext: Boolean
)

// 책 데이터 클래스
data class Book(
    @SerializedName("ISBN") val isbn: String,
    @SerializedName("bookCover") val bookCover: String,
    @SerializedName("bookTitle") val bookTitle: String,
    @SerializedName("author") val author: String
)