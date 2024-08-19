package com.example.readme.data.remote

data class KakaoResponse(
    val id: Long,
    val connected_at: String,
    val properties: Properties,
    val kakao_account: KakaoAccount
)

data class Properties(
    val nickname: String,
    val profile_image: String,
    val thumbnail_image: String
)

data class KakaoAccount(
    val profile: Profile,
    val has_email: Boolean,
    val email: String?
)

data class Profile(
    val nickname: String,
    val thumbnail_image_url: String,
    val profile_image_url: String
)
