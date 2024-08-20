package com.example.readme.data.remote
import com.google.gson.annotations.SerializedName

data class CommentPageInfo(
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("hasNext") val hasNext: Boolean
)

data class Comment(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("content") val content: String,
    @SerializedName("passedDate") val passedDate: String
)

data class CommentListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: List<Comment>
)
