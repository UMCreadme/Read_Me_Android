package com.example.readme.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.BookInfo
import com.example.readme.data.remote.ReadmeServerService
import kotlinx.coroutines.launch

class SearchBookViewModel(private val token: String, private val apiService: ReadmeServerService) : ViewModel() {
    private val TAG = SearchBookViewModel::class.java.simpleName

    // 현재 검색된 책 리스트를 관리하는 LiveData
    private val _searchBookItems = MutableLiveData<List<BookInfo>?>()
    val searchBookItems: MutableLiveData<List<BookInfo>?> get() = _searchBookItems

    // 현재 페이지 상태를 관리하는 변수들
    private var currentPage = 1
    private var lastQuery: String? = null
    private var hasNextPage = true

    // 책 검색
    fun searchBook(query: String, isNewSearch: Boolean = true): MutableLiveData<List<BookInfo>?> {
        if (isNewSearch) {
            // 새로운 검색이면 페이지 초기화
            currentPage = 1
            lastQuery = query
            hasNextPage = true
            _searchBookItems.value = emptyList() // 이전 검색 결과 초기화
        } else if (!hasNextPage) {
            return _searchBookItems// 더 이상 가져올 페이지가 없으면 return
        }

        viewModelScope.launch {
            try {
                // Retrofit API 호출
                val response = apiService.searchBooks("Bearer $token", query, currentPage, 50)

                // 응답이 성공일 경우
                if (response.isSuccess) {
                    val items = response.result ?: emptyList()

                    // 현재 페이지 결과를 기존 결과에 추가
                    val currentItems = _searchBookItems.value.orEmpty().toMutableList()
                    currentItems.addAll(items)
                    _searchBookItems.postValue(currentItems)

                    // 다음 페이지가 있는지 확인
                    hasNextPage = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e(TAG, "Failed to fetch search book items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching search book items", e)
            }
        }

        return _searchBookItems
    }

    // 다음 페이지를 불러오는 함수
    fun loadNextPage() {
        lastQuery?.let {
            searchBook(it, isNewSearch = false)
        }
    }
}
