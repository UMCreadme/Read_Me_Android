package com.example.readme.ui.community.my

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.MyCommunityListResponse
import com.example.readme.data.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityMyViewModel(
    private val repository: CommunityRepository
) : ViewModel() {
    private val TAG = CommunityMyViewModel::class.java.simpleName
    private val _myCommunityItems = MutableLiveData<List<MyCommunityListResponse>?>()
    val myCommunityItems: LiveData<List<MyCommunityListResponse>?> get() = _myCommunityItems

    private var currentPage = 1
    private var hasNext = true
    private var isLoading = false

    fun fetchMyCommunityList(isNew: Boolean = true) {
        if (isNew) {
            resetPagination()
        } else if (!hasNext || isLoading) {
            return
        }

        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMyCommunities(currentPage, 20)

                if (response.isSuccess) {
                    val items = response.result.filterNotNull()

                    val currentList = _myCommunityItems.value.orEmpty()
                    _myCommunityItems.postValue(currentList + items)

                    hasNext = response.pageInfo.hasNext
                    currentPage++
                } else {
                    Log.e(TAG, "Failed to fetch my community items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching my community items", e)
            } finally {
                isLoading = false
            }
        }
    }

    private fun resetPagination() {
        currentPage = 1
        hasNext = true
        _myCommunityItems.value = emptyList()
    }

    fun loadMore() {
        fetchMyCommunityList(false)
    }
}