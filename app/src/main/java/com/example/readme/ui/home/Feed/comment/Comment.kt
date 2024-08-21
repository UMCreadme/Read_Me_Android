package com.example.readme.ui.home.Feed.comment

import java.util.Date
data class Comment(
    val userId: Int,
    val account: String,
    val profileImg: String,
    val content: String,
    val passedDate: String,
//    val shortsId: String // 이 필드는 댓글이 달린 쇼츠의 ID를 나타낼 수 있습니다.
)
