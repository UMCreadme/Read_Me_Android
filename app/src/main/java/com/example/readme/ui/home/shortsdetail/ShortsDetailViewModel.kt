package com.example.readme.ui.home.shortsdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.ui.data.entities.like.LikeResponse
import com.example.readme.data.entities.detail.ShortsDetailInfo
import com.example.readme.data.entities.detail.ShortsDetailResponse
import com.example.readme.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch

class ShortsDetailViewModel : ViewModel() {

    // MutableLiveData를 ShortsDetail 리스트로 정의
    private val _shorts = MutableLiveData<List<ShortsDetailInfo>>()
    val shorts: LiveData<List<ShortsDetailInfo>> get() = _shorts

    // Shorts 상세 정보를 가져오는 메소드 (코루틴 사용)
    fun fetchShortsDetails(shortsId: Int, start: String, page: Int, size: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.getMainInfoService().getShortsDetailByFeeds(shortsId, start, page, size)

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val result = response.body()?.result ?: emptyList()
                    _shorts.postValue(result)
                    Log.d("fetchShortsDetails", "Fetched shorts details: $result")
                } else {
                    Log.d("fetchShortsDetails", "Response not successful")
                }
            } catch (e: Exception) {
                Log.d("fetchShortsDetails", "Failed to fetch shorts details: ${e.message}")
            }
        }
    }

    // Shorts 좋아요 기능 (코루틴 사용)
    fun likeShorts(shorts: ShortsDetailInfo) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.getMainInfoService().likeShorts(shorts.shortsId)

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val currentFeeds = _shorts.value ?: emptyList()

                    // updatedFeeds 리스트 생성
                    val updatedFeeds = currentFeeds.map {
                        if (it.shortsId == shorts.shortsId) {
                            it.copy(isLike = !it.isLike, likeCnt = response.body()?.result ?: it.likeCnt)
                        } else {
                            it
                        }
                    }

                    // updatedFeeds를 LiveData에 반영
                    _shorts.postValue(updatedFeeds)
                    Log.d("ShortsDetailViewModel", "Like updated for shorts: ${shorts.shortsId}")
                } else {
                    Log.d("ShortsDetailViewModel", "Like update failed")
                }
            } catch (e: Exception) {
                Log.d("ShortsDetailViewModel", "Failed to update like status: ${e.message}")
            }
        }
    }
}