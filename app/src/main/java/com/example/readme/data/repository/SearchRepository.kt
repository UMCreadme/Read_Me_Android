package com.example.readme.data.repository

import com.example.readme.data.remote.ReadmeServerService

object SearchRepository {

    private lateinit var apiService: ReadmeServerService

    // 초기화 메서드
    fun init(apiService: ReadmeServerService) {
        this.apiService = apiService
    }

    // 최근 검색어 조회
    suspend fun getRecentSearches() = apiService.getRecentSearches()

    // 책 미리보기 검색
    suspend fun searchBooksPreview(query: String, page: Int, size: Int) = apiService.searchBooksPreview(query, page, size)

    // 책 검색
    suspend fun searchBooks(query: String, page: Int, size: Int) = apiService.searchBooks(query, page, size)

    // 유저 검색
    suspend fun searchUsers(query: String, page: Int, size: Int) = apiService.searchUsers(query, page, size)

    // 최근 검색어 삭제
    suspend fun deleteRecentSearch(recentSearchesId: Int) = apiService.deleteRecentSearch(recentSearchesId)

    // 책 검색기록 저장
    suspend fun saveRecentSearchBook(isbn:String) = apiService.saveRecentSearchBook(isbn)

    // 책 상세 정보 조회
    suspend fun getBookDetail(isbn: String, page:Int, size: Int) = apiService.getBookDetail(isbn, page, size)
    suspend fun getBookDetail(bookId: Int, page:Int, size: Int) = apiService.getBookDetail(bookId, page, size)

    // 책 읽음 여부 업데이트
    suspend fun updateReadStatus(bookId: Int) = apiService.updateReadStatus(bookId)
    suspend fun updateReadStatus(isbn: String) = apiService.updateReadStatus(isbn)
}
