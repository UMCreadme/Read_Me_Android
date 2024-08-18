package com.example.readme.ui.network

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class UploadPreviewRequest(
    @SerializedName("isbn")
    val isbn: String,

    @SerializedName("bookTitle")
    val bookTitle: String,

    @SerializedName("cid")
    val cid: Int,

    @SerializedName("book_cover")
    val bookCover: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("link")
    val link: String,

    @SerializedName("phrase")
    val phrase: String,

    @SerializedName("phrase_x")
    val phraseX: Double,

    @SerializedName("phrase_y")
    val phraseY: Double,

    @SerializedName("shorts_title")
    val shortsTitle: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("tags")
    val tags: List<String>
)



