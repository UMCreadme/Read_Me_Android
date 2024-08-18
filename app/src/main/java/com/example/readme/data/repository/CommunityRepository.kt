package com.example.readme.data.repository

import com.example.readme.data.remote.ReadmeServerService

object CommunityRepository {

    private lateinit var apiService: ReadmeServerService

    // 초기화 메서드
    fun init(apiService: ReadmeServerService) {
        this.apiService = apiService
    }

    // 전체 모임 리스트 조회
    suspend fun getCommunities(page: Int, size: Int) = apiService.getCommunities(page, size)

    // 나의 모임 리스트 조회
    suspend fun getMyCommunities(page: Int, size: Int) = apiService.getMyCommunities(page, size)

    // 모임 검색
    suspend fun searchCommunities(keyword: String, page: Int, size: Int) = apiService.searchCommunities(keyword, page, size)
}