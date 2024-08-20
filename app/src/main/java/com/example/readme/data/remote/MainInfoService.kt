package com.example.readme.data.remote

import com.example.readme.data.entities.booklist.BookListResponse
import com.example.readme.data.entities.recentbook.RecentBookResponse
import com.example.readme.data.entities.category.CategoryFeedResponse
import com.example.readme.data.entities.detail.ShortsDetailResponse
import com.example.readme.data.entities.inithome.MainInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainInfoService {
    @GET("/home?page=1&size=20")
    fun getMainInfo(): Call<MainInfoResponse>

    @GET("/home/categories")
    fun getCategoryFeeds(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("category") category: String
    ): Call<CategoryFeedResponse>

    @GET("/shorts/{shortsId}")
    fun getShortsDetailByFeeds(
        @Path("shortsId") shortsId: Int,
        @Query("start") start: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 4
    ): Call<ShortsDetailResponse>

    @GET("/books/recent?page=1&size=20")
    fun getRecentBooks() : Call<RecentBookResponse>

    @GET("/books")
    fun getBookList(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 100,
        @Query("preview") preview: Boolean = true
    ) : Call<BookListResponse>


    companion object {
        const val BASE_URL = "https://api.umcreadme11.shop/"
    }
}



