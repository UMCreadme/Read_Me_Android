package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class RecentSearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<RecentSearch>?
)

data class RecentSearch(
    @SerializedName("query") val query: String,
    @SerializedName("recent_searches_id") val recentSearchesId: Int,
    @SerializedName("book_id") val bookId: Int? = null,
    @SerializedName("bookImg") val bookImg: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("author") val author: String? = null
)
