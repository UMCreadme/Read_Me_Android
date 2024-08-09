package com.example.readme.data.entities

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: List<UserInfo>
)

data class UserInfo(
    @SerializedName("userId") val userId: Int,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("account") val account: String,
    @SerializedName("nickname") val nickname: String
)
