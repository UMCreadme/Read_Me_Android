package com.example.readme.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.entities.CommunityItem

class CommunityViewModel: ViewModel() {
    private val _communityItems = MutableLiveData<List<CommunityItem>>()
    val communityItems: LiveData<List<CommunityItem>> get() = _communityItems

    init {
        _communityItems.value = listOf(
            CommunityItem("우리가 빛의 속도로 갈 수 있다면", "서울", 2, 6),
            CommunityItem("이어령의 마지막 수업", "부산", 2, 6),
            CommunityItem("이어령의 마지막 수업", "부산", 2, 6),
            CommunityItem("이어령의 마지막 수업", "부산", 2, 6),
            CommunityItem("이어령의 마지막 수업", "부산", 2, 6)

        )
    }
}