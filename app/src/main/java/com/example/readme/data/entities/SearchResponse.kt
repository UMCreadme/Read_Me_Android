package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class BookSearchResult(
    @SerializedName("ISBN") val ISBN: String,
    @SerializedName("bookCover") val bookImg: String,
    @SerializedName("bookTitle") val title: String,
    @SerializedName("author") val author: String
)

data class RecentSearch(
    @SerializedName("query") val query: String,
    @SerializedName("recent_searches_id") val recentSearchesId: Int,
    @SerializedName("book_id") val bookId: Int? = null,
    @SerializedName("bookImg") val bookImg: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("author") val author: String? = null
)

data class UserInfo(
    @SerializedName("userId") val userId: Int,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("account") val account: String,
    @SerializedName("nickname") val nickname: String
)