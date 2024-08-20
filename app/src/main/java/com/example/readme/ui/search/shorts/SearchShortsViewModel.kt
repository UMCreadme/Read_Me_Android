package com.example.readme.ui.search.shorts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.SearchShortsResult
import com.example.readme.data.repository.SearchRepository
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchShortsViewModel(
    private val repository: SearchRepository
) : ViewModel() {
    private val TAG = SearchShortsViewModel::class.java.simpleName
    private val _searchShortsItems = MutableLiveData<List<SearchShortsResult>?>()
    val searchShortsItems: LiveData<List<SearchShortsResult>?> get() = _searchShortsItems

    private var currentPage = 1
    private var lastQuery: String? = null
    private var hasNext = true
    private var isLoading = false

    fun searchShorts(query: String, isNewSearch: Boolean = true) {
        if (isNewSearch) {
            // 새로운 검색이면 페이지 초기화
            currentPage = 1
            lastQuery = query
            hasNext = true
            _searchShortsItems.value = emptyList() // 이전 검색 결과 초기화
        } else if (!hasNext || isLoading) {
            return // 더 이상 가져올 페이지가 없거나 로딩 중이면 return
        }

        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Retrofit API 호출
                val response = repository.searchShorts(query, currentPage, 50)

                // 응답이 성공일 경우
                if (response.isSuccess) {
                    // response.result의 타입을 확인하여 적절히 캐스팅합니다.
                    val items = response.result as? List<SearchShortsResult> ?: emptyList()

                    // 현재 페이지 결과를 기존 결과에 추가
                    val currentList = _searchShortsItems.value.orEmpty()
                    _searchShortsItems.postValue(currentList + items)

                    // 다음 페이지가 있는지 확인
                    hasNext = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e(TAG, "Failed to fetch search shorts items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching search shorts items", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun likeShorts(item: SearchShortsResult) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getMainInfoService().likeShorts(item.shortsId)
                }
                if (response.body()?.isSuccess == true) {
                    val updatedFeeds = _searchShortsItems.value?.map {
                        if (it.shortsId == item.shortsId) {
                            it.copy(isLike = !it.isLike, likeCnt = response.body()?.result ?: it.likeCnt)
                        } else {
                            it
                        }
                    } ?: emptyList()
                    _searchShortsItems.postValue(updatedFeeds)
                } else {
                    Log.d("FeedViewModel", "Like update failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.d("FeedViewModel", "Failed to update like status: ${e.message}")
            }
        }
    }

    // 다음 페이지를 불러오는 함수
    fun loadNextPage() {
        if (!hasNext || isLoading) {
            return
        }
        lastQuery?.let { searchShorts(it, false) }
    }
}
