package com.example.readme.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentListService {

    @GET("/shorts/{shortsId}/comments")
    suspend fun getCommentList(
        @Path("shortsId") shortsId: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): CommentListResponse

    @POST("/shorts/{shortsId}/comments")
    suspend fun sendMessage(
        @Path("shortsId") shortsId: String,
        @Body newMessage: com.example.readme.data.remote.Comment
    ):PostResponse

}