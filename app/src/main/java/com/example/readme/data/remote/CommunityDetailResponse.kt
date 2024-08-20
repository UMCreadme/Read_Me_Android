package com.example.readme.data.remote

import android.widget.ImageView
import com.example.readme.ui.community.Community

data class CommunityDetailResponse(
    val communityId: Int,           // 커뮤니티 ID
    val isParticipating: Boolean,   // 사용자가 참여 중인지 여부
    val title: String?,             // 커뮤니티 제목
    val leader: Leader?,            // 커뮤니티 리더 정보
    val location: String?,          // 커뮤니티 위치
    val createdAt: String?,         // 생성일
    val tags: List<String>?,        // 태그 목록
    val capacity: Int?,             // 최대 정원
    val content: String?,
    val currentMembers: Int?,       // 현재 가입한 멤버 수
    val isSuccess: Boolean,          // 응답 성공 여부
    val result: Community?
)
data class Leader(
    val imageUrl: String?,         // 리더 이미지 URL
    val account: String?,          // 리더 계정명
    val nickname: String?,         // 리더 닉네임
    val userId: Int?               // 리더의 사용자 ID
)
