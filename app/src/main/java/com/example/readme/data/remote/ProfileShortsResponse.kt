package com.example.readme.data.remote

data class ProfileShortsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val pageInfo: ProfileShortsPageInfo,
    val result: List<ProfileShortsItem>
)

data class ProfileShortsPageInfo(
    val page: Int,
    val size: Int,
    val hasNext: Boolean
)

data class ProfileShortsItem(
    val shortsId: Int,
    val shortsImage: String,
    val shortsPhrase: String,
    val shortsBookTitle: String,
    val shortsBookAuthor: String
)
