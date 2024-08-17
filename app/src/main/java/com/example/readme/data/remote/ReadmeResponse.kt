package com.example.readme.data.remote

import com.google.gson.annotations.SerializedName

// 기본 응답
data class Response (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)

// 데이터가 있는 응답 - 기본 응답에 데이터만 추가
data class ResponseWithData<T> (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: T
)

// 페이지네이션 정보가 있는 응답
data class ResponseWithPagination<T> (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("result") val result: T
)

data class PageInfo(
    val page: Int,
    val size: Int,
    val hasNext: Boolean
)