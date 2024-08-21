package com.example.readme.ui.community.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.CommunityDetailResponse
import com.example.readme.data.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunityDetailViewModel(
    private val repository: CommunityRepository
) : ViewModel() {
    private val _communityDetail = MutableLiveData<CommunityDetailResponse?>()
    val communityDetail: LiveData<CommunityDetailResponse?> get() = _communityDetail

    private val _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> get() = _errMessage

    fun fetchCommunityDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCommunityDetail(id)
                if(response.isSuccess) {
                    _communityDetail.postValue(response.result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun joinCommunity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.joinCommunity(id)
                if(response.isSuccess) {
                    fetchCommunityDetail(id)
                } else {
                    _errMessage.postValue("${response.message}")
                    Log.e("CommunityDetailViewModel", "Failed to join community: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                _errMessage.postValue("한번 더 시도해주세요.")
                Log.e("CommunityDetailViewModel", "Failed to join community", e)
            }
        }
    }
}