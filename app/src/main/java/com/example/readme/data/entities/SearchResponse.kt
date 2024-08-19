package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class BookSearchResult(
    @SerializedName("ISBN") val ISBN: String,
    @SerializedName("bookCover") val bookImg: String,
    @SerializedName("bookTitle") val title: String,
    @SerializedName("author") val author: String
)

data class BookDetailResponse(
    @SerializedName("book") val bookDetail: BookDetail,
    @SerializedName("shorts") val shorts: List<ShortsPreview>
)

data class BookDetail(
    @SerializedName("bookId") val bookId: Int? = null,
    @SerializedName("ISBN") val ISBN: String,
    @SerializedName("bookCover") val bookImg: String,
    @SerializedName("bookTitle") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("link") val link: String,
    @SerializedName("isRead") var isRead: Boolean
)

data class ShortsPreview(
    @SerializedName("shortsId") val shortsId: Int,
    @SerializedName("shortsImg") val shortsImg: String,
    @SerializedName("phrase") val phrase: String
)

data class SearchShortsResult(
    @SerializedName("userId") val userId: Int,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("shortsId") val shortsId: Int,
    @SerializedName("shortsImg") val shortsImg: String,
    @SerializedName("phrase") val phrase: String,
    @SerializedName("phraseX") val phraseX: Float,
    @SerializedName("phraseY") val phraseY: Float,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("isLike") val isLike: Boolean,
    @SerializedName("likeCnt") val likeCnt: Int,
    @SerializedName("commentCnt") val commentCnt: Int,
    @SerializedName("postingDate") val postingDate: String
)

data class RecentSearch(
    @SerializedName("query") val query: String,
    @SerializedName("recent_searches_id") val recentSearchesId: Int,
    @SerializedName("book_id") val bookId: Int? = null,
    @SerializedName("bookImg") val bookImg: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("author") val author: String? = null
)

data class SearchUserResult(
    @SerializedName("userId") val userId: Int,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("account") val account: String,
    @SerializedName("nickname") val nickname: String
)
