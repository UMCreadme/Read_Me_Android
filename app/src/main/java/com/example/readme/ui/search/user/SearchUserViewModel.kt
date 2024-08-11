package com.example.readme.ui.search.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.UserInfo
import com.example.readme.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchUserViewModel(
    private val repository: SearchRepository
) : ViewModel() {
    private val TAG = SearchUserViewModel::class.java.simpleName
    private val _searchUserItems = MutableLiveData<List<UserInfo>?>()
    val searchUserItems: LiveData<List<UserInfo>?> get() = _searchUserItems

    private var currentPage = 1
    private var lastQuery: String? = null
    private var hasNext = true
    private var isLoading = false

    fun searchUser(query: String, isNewSearch: Boolean = true) {
        if (isNewSearch) {
            // 새로운 검색이면 페이지 초기화
            currentPage = 1
            lastQuery = query
            hasNext = true
            _searchUserItems.value = emptyList() // 이전 검색 결과 초기화
        } else if (!hasNext || isLoading) {
            return // 더 이상 가져올 페이지가 없거나 로딩 중이면 return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                // Retrofit API 호출
                val response = repository.searchUsers(query, currentPage, 50)

                // 응답이 성공일 경우
                if (response.isSuccess) {
                    val items = response.result

                    // 현재 페이지 결과를 기존 결과에 추가
                    val currentList = _searchUserItems.value.orEmpty()
                    _searchUserItems.postValue(currentList + items)

                    // 다음 페이지가 있는지 확인
                    hasNext = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e(TAG, "Failed to fetch search user items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching search user items", e)
            } finally {
                isLoading = false
            }
        }
    }

    // 다음 페이지를 불러오는 함수
    fun loadNextPage() {
        if (!hasNext || isLoading) return
        lastQuery?.let { searchUser(it, false) }
    }
}