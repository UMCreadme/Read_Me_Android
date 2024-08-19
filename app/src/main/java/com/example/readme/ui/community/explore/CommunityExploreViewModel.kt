package com.example.readme.ui.community.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.CommunityListResponse
import com.example.readme.data.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class CommunityExploreViewModel(
    private val repository: CommunityRepository
) : ViewModel() {
    private val _communityItems = MutableLiveData<List<CommunityListResponse>?>()
    val communityItems: LiveData<List<CommunityListResponse>?> get() = _communityItems

    private val queryFlow = MutableStateFlow("")
    private var currentPage = 1
    private var lastQuery: String? = null
    private var hasNext = true
    private var isLoading = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            queryFlow
                .debounce(400) // 400ms debounce time to prevent excessive API calls
                .distinctUntilChanged() // Only proceed if the query actually changes
                .collect { query ->
                    if (query.isEmpty()) {
                        fetchCommunityList() // 검색어가 비어 있을 때 전체 커뮤니티 리스트 조회
                    } else {
                        searchCommunity(query)
                    }
                }
        }
    }

    fun fetchCommunityList(isNew: Boolean = true) {
        if (isNew) {
            resetPagination()
        } else if (!hasNext || isLoading) {
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.getCommunities(currentPage, 20)

                if (response.isSuccess) {
                    Log.d("CommunityExploreViewModel", "Fetched community items: ${response.result}")
                    val items = response.result.filterNotNull()

                    val currentList = _communityItems.value.orEmpty()
                    _communityItems.postValue(currentList + items)

                    hasNext = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e("CommunityExploreViewModel", "Failed to fetch community items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("CommunityExploreViewModel", "Error fetching community items", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun searchCommunity(query: String, isNewSearch: Boolean = true) {
        if (isNewSearch) {
            currentPage = 1
            lastQuery = query
            hasNext = true
            _communityItems.value = emptyList()
        } else if (!hasNext || isLoading) {
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.searchCommunities(query, currentPage, 20)

                if (response.isSuccess) {
                    val items = response.result.filterNotNull()

                    val currentList = _communityItems.value.orEmpty()
                    _communityItems.postValue(currentList + items)

                    hasNext = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e("CommunityExploreViewModel", "Failed to fetch search community items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("CommunityExploreViewModel", "Error fetching search community items", e)
            } finally {
                isLoading = false
            }
        }
    }

    private fun resetPagination() {
        currentPage = 1
        hasNext = true
    }

    fun loadDefaultCommunityMore() {
        if (!hasNext || isLoading) return
        fetchCommunityList(false)
    }

    fun loadSearchCommunityMore() {
        if (!hasNext || isLoading) return
        lastQuery?.let { searchCommunity(it, false) }
    }
}
