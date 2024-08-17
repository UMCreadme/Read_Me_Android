package com.example.readme.ui.community

data class Community(
    val imageResId: Int,  // 이미지 리소스 ID
    val location: String,  // 위치 정보
    val title: String,     // 커뮤니티 제목
    val currentMembers: Int,  // 현재 멤버 수
    val totalMembers: Int,  // 총 멤버 수
    val tags: List<String>  // 태그 리스트
)
