package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class SearchBookResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: List<BookInfo>
)

data class PageInfo(
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("hasNext") val hasNext: Boolean,
)

data class BookInfo(
    @SerializedName("ISBN") val ISBN: String,
    @SerializedName("bookCover") val bookImg: String,
    @SerializedName("bookTitle") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("cid") val cid: Int,
    @SerializedName("mallType") val mallType: String,
    @SerializedName("link") val link: String
)
