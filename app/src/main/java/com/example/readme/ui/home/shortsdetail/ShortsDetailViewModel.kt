package com.example.readme.ui.home.shortsdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.ui.data.entities.like.LikeResponse
import com.example.readme.data.entities.detail.ShortsDetailInfo
import com.example.readme.data.entities.detail.ShortsDetailResponse
import com.example.readme.data.entities.inithome.FeedInfo
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    fun likeShorts(shorts: ShortsDetailInfo) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getMainInfoService().likeShorts(shorts.shortsId)
                }
                if (response.body()?.isSuccess == true) {
                    val updatedFeeds = _shorts.value?.map {
                        if (it.shortsId == shorts.shortsId) {
                            it.copy(isLike = !it.isLike, likeCnt = response.body()?.result ?: it.likeCnt)
                        } else {
                            it
                        }
                    } ?: emptyList()
                    _shorts.value = updatedFeeds
//                    Log.d("FeedViewModel", "Like updated for feed: ${feed.shortsId}")
                } else {
                    Log.d("FeedViewModel", "Like update failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.d("FeedViewModel", "Failed to update like status: ${e.message}")
            }
        }
    }

    // 좋아요 상태를 업데이트하고, 필요한 경우 데이터를 다시 불러오는 함수
    fun updateLikeStatus(item: ShortsDetailInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            // 현재 searchShortsItems의 item에 해당하는 부분 업데이트
            val updatedItems = _shorts.value.orEmpty().map {
                if (it.shortsId == item.shortsId) {
                    it.copy(isLike = item.isLike, likeCnt = item.likeCnt)
                } else {
                    it
                }
            }
            _shorts.postValue(updatedItems)
        }
    }

}