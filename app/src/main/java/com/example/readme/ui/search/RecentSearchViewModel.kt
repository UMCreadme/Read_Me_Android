package com.example.readme.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.RecentSearch
import com.example.readme.data.remote.ReadmeServerService
import kotlinx.coroutines.launch

class RecentSearchViewModel(private val token: String, private val apiService: ReadmeServerService) : ViewModel() {
    private val TAG = RecentSearchViewModel::class.java.simpleName
    private val _recentSearchItems = MutableLiveData<List<RecentSearch>?>()
    val recentSearchItems: MutableLiveData<List<RecentSearch>?> get() = _recentSearchItems

    fun fetchRecentSearchItems(): MutableLiveData<List<RecentSearch>?> {
        viewModelScope.launch {
            try {
                // Retrofit API 호출
                val response = apiService.getRecentSearches("Bearer $token")

                // 응답이 성공일 경우
                if(response.isSuccess){
                    val items = response.result
                    _recentSearchItems.postValue(items)
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
        Log.i(TAG, "Removing search item: $searchItem")
        _recentSearchItems.value = _recentSearchItems.value?.filter { it != searchItem }
    }
}
