package com.example.readme.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.entities.UserInfo

class SearchUserViewModel : ViewModel() {
    // LiveData를 사용하여 사용자 검색 목록을 관리
     private val _searchUserItems = MutableLiveData<List<UserInfo>>()
        val searchUserItems: MutableLiveData<List<UserInfo>> get() = _searchUserItems

    // 사용자 검색
    fun searchUser(query: String) {
        // 검색어를 서버로 전송하고, 검색 결과를 받아오는 로직
        // 받아온 검색 결과를 UserInfo 객체로 변환하여 _searchUserItems에 추가
        // 예시 데이터
        _searchUserItems.value = listOf(
            UserInfo(
                userId = 1,
                profileImg = "https://readme-image.s3.ap-northeast-2.amazonaws.com/profile/default-profile.png",
                account = "ossu-dev",
                nickname = "ossu"
            ),
            UserInfo(
                userId = 2,
                profileImg = "https://readme-image.s3.ap-northeast-2.amazonaws.com/profile/default-profile.png",
                account = "kimtomato",
                nickname = "멍이"
            ),
        )
    }
}