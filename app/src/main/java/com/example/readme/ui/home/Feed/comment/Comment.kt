package com.example.readme.ui.home.Feed.comment

import java.util.Date

data class Comment(
    val text: String,
    val time: Date = Date() // 댓글 작성 시간을 현재 시간으로 기본 설정
)
