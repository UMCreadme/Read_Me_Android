package com.example.readme.data.remote

data class ProfileBooksResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val pageInfo: PageInfo,
    val result: List<Book>
)

// 페이지 정보 클래스
data class PageInfo(
    val page: Int,
    val size: Int,
    val hasNext: Boolean
)

// 책 정보 클래스
data class Book(
    val bookImage: String,
    val bookTitle: String,
    val bookAuthor: String,
    val bookTranslator: String
)