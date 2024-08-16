package com.example.readme.ui.search.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.BookSearchResult
import com.example.readme.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchBookViewModel(
    private val repository: SearchRepository
) : ViewModel() {
    private val TAG = SearchBookViewModel::class.java.simpleName
    private val _searchBookItems = MutableLiveData<List<BookSearchResult>?>()
    val searchBookItems: LiveData<List<BookSearchResult>?> get() = _searchBookItems

    private var currentPage = 1
    private var lastQuery: String? = null
    private var hasNext = true
    private var isLoading = false

    fun searchBook(query: String, isNewSearch: Boolean = true) {
        if (isNewSearch) {
            // 새로운 검색이면 페이지 초기화
            currentPage = 1
            lastQuery = query
            hasNext = true
            _searchBookItems.value = emptyList() // 이전 검색 결과 초기화
        } else if (!hasNext || isLoading) {
            return // 더 이상 가져올 페이지가 없거나 로딩 중이면 return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                // Retrofit API 호출
                val response = repository.searchBooks(query, currentPage, 50)

                // 응답이 성공일 경우
                if (response.isSuccess) {
                    val items = response.result.filterNotNull()

                    // 현재 페이지 결과를 기존 결과에 추가
                    val currentList = _searchBookItems.value.orEmpty()
                    _searchBookItems.postValue(currentList + items)

                    // 다음 페이지가 있는지 확인
                    hasNext = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e(TAG, "Failed to fetch search book items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching search book items", e)
            } finally {
                isLoading = false
            }
        }
    }

    // 다음 페이지를 불러오는 함수
    fun loadNextPage() {
        if (!hasNext || isLoading) return
        lastQuery?.let { searchBook(it, false) }
    }
}
