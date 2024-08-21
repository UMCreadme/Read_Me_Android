package com.example.readme.data.repository

import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.entities.UserData
import com.example.readme.data.remote.ResponseWithData
import com.example.readme.data.LoginResult
import com.example.readme.data.remote.KakaoLoginService

object LoginRepository {
    private lateinit var apiService: KakaoLoginService

    // 초기화 메서드
    fun init(apiService: KakaoLoginService) {
        LoginRepository.apiService = apiService
    }

    suspend fun sendKakaoUserInfo(user: KaKaoUser): ResponseWithData<LoginResult> {
        return apiService.sendKakaoUserInfo(user)
    }

    suspend fun sendSignUpInfo(user: UserData): ResponseWithData<LoginResult?> {
        return apiService.sendSignUpInfo(user)
    }
}
