package com.example.readme.data.entities.detail

import com.example.readme.data.remote.PageInfo

data class ShortsDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val pageInfo: PageInfo,
    val result: List<ShortsDetailInfo>
)


data class ShortsDetailInfo(
    val userId: Int,
    val userAccount: String,
    val profileImg: String,
    val isFollow: Boolean,
    val shortsId : Int,
    val shortsImg: String,
    val phrase: String,
    val phraseX: Int,
    val phraseY: Int,
    val title: String,
    val content: String,
    val tags: List<String>,
    val isLike: Boolean,
    val likeCnt: Int,
    val commentCnt: Int,
    val bookId: Int
)
