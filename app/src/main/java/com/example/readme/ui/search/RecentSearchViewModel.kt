package com.example.readme.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.RecentSearch
import com.example.readme.data.repository.SearchRepository
import kotlinx.coroutines.launch

class RecentSearchViewModel(
    private val repository: SearchRepository
): ViewModel() {
    private val TAG = RecentSearchViewModel::class.java.simpleName
    private val _recentSearchItems = MutableLiveData<List<RecentSearch>?>()
    val recentSearchItems: LiveData<List<RecentSearch>?> get() = _recentSearchItems

    fun fetchRecentSearchItems(): LiveData<List<RecentSearch>?> {
        viewModelScope.launch {
            try {
                // Repository를 통해 최근 검색 목록을 가져옴
                val response = repository.getRecentSearches()

                // 응답이 성공일 경우
                if(response.isSuccess){
                    val items = response.result
                    _recentSearchItems.postValue(items)
                    Log.d("RecentSearchViewModel", "response:${response.result}")
                } else if(response.code == "TOKEN4005"){
                    // 액세스 토큰 재발급 후 재요청?
                    Log.e(TAG, "Failed to fetch recent search items: ${response.code} - ${response.message}")
                } else {
                    Log.e(TAG, "Failed to fetch recent search items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching recent search items", e)
            }
        }

        return _recentSearchItems
    }

    fun addSearchItem(searchItem: RecentSearch) {
        val currentItems = _recentSearchItems.value ?: listOf()
        _recentSearchItems.value = currentItems + searchItem
    }

    fun removeSearchItem(searchItem: RecentSearch) {
        viewModelScope.launch {
            try {
                // Repository를 통해 최근 검색 목록을 삭제
                val response = repository.deleteRecentSearch(searchItem.recentSearchesId)

                // 응답이 성공일 경우
                if(response.isSuccess){
                    _recentSearchItems.value = _recentSearchItems.value?.filter { it != searchItem }
                } else if(response.code == "TOKEN4005"){
                    // 액세스 토큰 재발급 후 재요청?
                    Log.e(TAG, "Failed to delete recent search item: ${response.code} - ${response.message}")
                } else {
                    Log.e(TAG, "Failed to delete recent search item: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting recent search item", e)
            }
        }
    }
}
