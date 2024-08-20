package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class CommunityListResponse(
    @SerializedName("bookImg") val bookImg: String,
    @SerializedName("bookTitle") val title: String,
    @SerializedName("Participants") val participants: Int,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("location") val location: String,
    @SerializedName("community_id") val communityId: Int
)

data class MyCommunityListResponse(
    @SerializedName("bookImg") val bookImg: String,
    @SerializedName("bookTitle") val title: String,
    @SerializedName("Participants") val participants: Int,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("location") val location: String,
    @SerializedName("unReadCount") val unReadCount: Int,
    @SerializedName("community_id") val communityId: Int
)

data class CommunityDetailResponse(
    @SerializedName("book") val book: CommunityBook,
    @SerializedName("leader") val leader: CommunityLeader,
    @SerializedName("location") val location: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("content") val content: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("currentMembers") val currentMembers: Int,
    @SerializedName("isParticipating") val isParticipating: Boolean
)

data class CommunityBook(
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("imageUrl") val imageUrl: String
)

data class CommunityLeader(
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("account") val account: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("userId") val userId: Int
)