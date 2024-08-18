package com.example.readme.ui.community.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.CommunityListResponse
import com.example.readme.data.repository.CommunityRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class CommunityExploreViewModel(
    private val repository: CommunityRepository
) : ViewModel() {
    private val _communityItems = MutableLiveData<List<CommunityListResponse>?>()
    val communityItems: LiveData<List<CommunityListResponse>?> get() = _communityItems

    private var currentPage = 1
    private var lastQuery: String? = null
    private var hasNext = true
    private var isLoading = false

    fun fetchCommunityList() {
        if (!hasNext || isLoading) {
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

    fun loadDefaultCommunityMore() {
        if (!hasNext || isLoading) return
        fetchCommunityList()
    }

    fun loadSearchCommunityMore() {
        if (!hasNext || isLoading) return
        lastQuery?.let { searchCommunity(it, false) }
    }
}