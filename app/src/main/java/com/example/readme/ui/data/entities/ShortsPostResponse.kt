package com.example.readme.ui.data.entities

import com.google.gson.annotations.SerializedName

data class ShortsPostResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
