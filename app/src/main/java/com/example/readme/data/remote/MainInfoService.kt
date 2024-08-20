package com.example.readme.data.remote

import com.example.readme.BuildConfig
import com.example.readme.data.entities.booklist.BookListResponse
import com.example.readme.data.entities.recentbook.RecentBookResponse
import com.example.readme.data.entities.category.CategoryFeedResponse
import com.example.readme.data.entities.detail.ShortsDetailResponse
import com.example.readme.data.entities.inithome.MainInfoResponse
import com.example.readme.ui.data.entities.like.LikeResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response


interface MainInfoService {
    @GET("/home?page=1&size=10")
    suspend fun getMainInfo(): Response<MainInfoResponse>
    @GET("/home/categories")
    suspend fun getCategoryFeeds(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("category") category: String
    ): Response<CategoryFeedResponse>

    @GET("/shorts/{shortsId}")
    suspend fun getShortsDetailByFeeds(
        @Path("shortsId") shortsId: Int,
        @Query("start") start: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 4
    ): Response<ShortsDetailResponse>

    @GET("/books/recent?page=1&size=20")
    suspend fun getRecentBooks(): Response<RecentBookResponse>

    @GET("/books")
    suspend fun getBookList(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("preview") preview: Boolean = true
    ): Response<BookListResponse>

    @POST("/shorts/{shortsId}/likes")
    suspend fun likeShorts(
        @Path("shortsId") shortsId: Int
    ): Response<LikeResponse>

    companion object {
        const val BASE_URL = BuildConfig.SERVER_URL
    }
}
