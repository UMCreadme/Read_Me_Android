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

    // 특정 커뮤니티에 사용자가 가입했는지 확인
    suspend fun isUserJoinedCommunity(communityId: Int): Boolean {
        // 사용자가 가입한 커뮤니티 목록에서 해당 커뮤니티 ID가 있는지 확인
        val response = apiService.getMyCommunities(1, 100) // 1페이지에 100개의 커뮤니티를 가져오는 예시
        return response.result.any { it.communityId == communityId }
    }

    // 커뮤니티 정보 가져오기
    suspend fun getCommunityInfo(communityId: Int, isParticipating: Boolean): com.example.readme.data.entities.CommunityDetailResponse {
        // 초기화 확인
        check(::apiService.isInitialized) { "ReadmeServerService is not initialized" }
        return apiService.getCommunityInfo(communityId)
    }
}
