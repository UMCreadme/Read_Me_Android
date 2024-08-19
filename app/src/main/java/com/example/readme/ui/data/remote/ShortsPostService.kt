package com.example.readme.ui.data.remote

import com.example.readme.ui.data.entities.ShortsPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ShortsPostService {
    @Multipart
    @POST("/shorts?directory=shorts")
    fun uploadShorts(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): Call<ShortsPostResponse>

    companion object {
        const val BASE_URL = "https://api.umcreadme11.shop/"
    }

}