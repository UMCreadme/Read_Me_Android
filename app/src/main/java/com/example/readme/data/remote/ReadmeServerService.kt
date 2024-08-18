package com.example.readme.data.remote

import com.example.readme.data.entities.BookDetailResponse
import com.example.readme.data.entities.BookSearchResult
import com.example.readme.data.entities.CommunityListResponse
import com.example.readme.data.entities.MyCommunityListResponse
import com.example.readme.data.entities.RecentSearch
import com.example.readme.data.entities.UserInfo
import com.example.readme.data.entities.recentbook.Book
import com.example.readme.ui.community.Chat
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReadmeServerService {

    /**
     * USER 관련 API
     */
    @GET("/users/my")
    suspend fun getMyProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @GET("/users/my/shorts")
    suspend fun getMyShorts(): ProfileShortsResponse

    // userId를 경로 매개변수로 받아서 요청
    @GET("/users/{userId}")
    suspend fun getProfile(
        //@Header("Authorization") token: String,
        @Path("userId") userId: String
    ): ProfileResponse

    @GET("/users/{userId}/shorts")
    suspend fun getShorts(
        @Path("userId") userId: String
    ): ProfileShortsResponse

    @GET("/users")
    suspend fun searchUsers(
        @Query("keyword") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ResponseWithPagination<List<UserInfo>>

    /**
     * COMMUNITY 관련 API
     */

    @POST("/communities/{communityId}/messages")
    suspend fun postMessage(@Path("communityId") communityId: String, @Body chat: Chat): Call<Chat>

    @GET("/communities/{communityId}/messages")
    suspend fun getMessages(@Path("communityId") communityId: String): Call<List<Chat>>

    @GET("/communities")
    suspend fun getCommunities(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 50,
    ) : ResponseWithPagination<List<CommunityListResponse>>

    @GET("/communities/my")
    suspend fun getMyCommunities(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 50,
    ) : ResponseWithPagination<List<MyCommunityListResponse>>

    @GET("/communities/search")
    suspend fun searchCommunities(
        @Query("keyword") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 50,
    ) : ResponseWithPagination<List<CommunityListResponse>>

    /**
     * RECENT-SEARCH 관련 API
     */

    @GET("/recent-searches")
    suspend fun getRecentSearches(): ResponseWithData<List<RecentSearch>>

    @DELETE("/recent-searches/{recentSearchesId}")
    suspend fun deleteRecentSearch(
        @Path("recentSearchesId") recentSearchesId: Int
    ): Response

    /**
     * BOOK 관련 API
     */

    @GET("/books")
    suspend fun searchBooksPreview(
        @Query("keyword") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 50,
        @Query("preview") preview: Boolean = true
    ): ResponseWithPagination<List<BookSearchResult>>

    @GET("/books")
    suspend fun searchBooks(
        @Query("keyword") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 50,
        @Query("preview") preview: Boolean = false
    ): ResponseWithPagination<List<BookSearchResult>>

    @POST("/books/{isbn}")
    suspend fun saveRecentSearchBook(
        @Path("isbn") isbn: String
    ): Response

    @GET("/books/{isbn}")
    suspend fun getBookDetail(
        @Path("isbn") isbn: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("isBookId") isBookId: Boolean = false
    ): ResponseWithPagination<BookDetailResponse>

    @GET("/books/{bookId}")
    suspend fun getBookDetail(
        @Path("bookId") bookId: Int,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("isBookId") isBookId: Boolean = true
    ): ResponseWithPagination<BookDetailResponse>

    @POST("/books/{bookId}/read")
    suspend fun updateReadStatus(
        @Path("bookId") bookId: Int,
        @Query("isBookId") isBookId: Boolean = true
    ): Response

    @POST("/books/{isbn}/read")
    suspend fun updateReadStatus(
        @Path("isbn") isbn: String,
        @Query("isBookId") isBookId: Boolean = false
    ): Response

    @GET("/books/recent")
    suspend fun getRecentSelectBooks(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ResponseWithPagination<List<Book>>

    companion object {
        const val BASE_URL ="https://api.umcreadme11.shop"
    }
}