package com.example.readme.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.entities.RecentSearch

class RecentSearchViewModel : ViewModel() {
    // 검색된 항목 목록을 관리하는 LiveData
    private val _recentSearchItems = MutableLiveData<List<RecentSearch>>()
    val recentSearchItems: LiveData<List<RecentSearch>> get() = _recentSearchItems

    init {
        // 초기 예시 데이터 설정
        _recentSearchItems.value = listOf(
            RecentSearch(
                query = "고요의",
                recentSearchesId = 15,
                bookId = 1470,
                bookImg = "https://image.aladin.co.kr/product/34312/24/coversum/8932924376_1.jpg",
                title = "고요의 바다에서",
                author = "에밀리 세인트존 맨델"
            ),
            RecentSearch(query = "김초엽", recentSearchesId = 5),
            RecentSearch(query = "ㄱ", recentSearchesId = 11),
            RecentSearch(query = "판타지", recentSearchesId = 10)
        )
    }

    // 새로운 검색 항목을 추가하는 함수
    fun addSearchItem(searchItem: RecentSearch) {
        val currentItems = _recentSearchItems.value ?: listOf()
        _recentSearchItems.value = currentItems + searchItem
    }

    // 특정 검색 항목을 제거하는 함수
    fun removeSearchItem(searchItem: RecentSearch) {
        _recentSearchItems.value = _recentSearchItems.value?.filter { it != searchItem }
    }
}