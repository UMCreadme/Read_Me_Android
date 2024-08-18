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