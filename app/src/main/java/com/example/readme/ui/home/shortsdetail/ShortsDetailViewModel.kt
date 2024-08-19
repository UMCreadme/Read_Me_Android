package com.example.readme.ui.home.shortsdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.ui.data.entities.like.LikeResponse
import com.example.readme.data.entities.detail.ShortsDetailInfo
import com.example.readme.data.entities.detail.ShortsDetailResponse
import com.example.readme.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShortsDetailViewModel : ViewModel() {

    // MutableLiveData를 ShortsDetail 리스트로 정의
    private val _shorts = MutableLiveData<List<ShortsDetailInfo>>()
    val shorts: LiveData<List<ShortsDetailInfo>> get() = _shorts

    // Shorts 상세 정보를 가져오는 메소드
    fun fetchShortsDetails(shortsId: Int, start: String, page: Int, size: Int) {
        RetrofitClient.getMainInfoService().getShortsDetailByFeeds(shortsId, start, page, size).enqueue(object : Callback<ShortsDetailResponse> {
                override fun onResponse(call: Call<ShortsDetailResponse>, response: Response<ShortsDetailResponse>) {
                    Log.d("fetchShortsDetails", "shortsId,${shortsId}")
                    if (response.body()?.isSuccess == true) {
                        // 결과를 ShortsDetail 리스트로 변환
                        Log.d("fetchShortsDetails", "성공,${response.body()}")

                        val result = response.body()?.result ?: emptyList()
                        _shorts.postValue(result)
                    } else {
                        // 오류 처리
                        Log.d("fetchShortsDetails", "오류,${response.body()}")
                    }
                }

                override fun onFailure(call: Call<ShortsDetailResponse>, t: Throwable) {
                    // 오류 처리
                }
            })
    }

    fun likeShorts(shorts : ShortsDetailInfo) {
        RetrofitClient.getMainInfoService().likeShorts(shorts.shortsId).enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.body()?.isSuccess == true) {
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
                    Log.d("ShortsDetailViewModel", "Like updated for feed: ${shorts.shortsId}")
                } else {
                    // 오류 처리
                    Log.d("ShortsDetailViewModel", "Like update failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                // 오류 처리
                Log.d("FeedViewModel", "Failed to update like status: ${t.message}")
            }
        })
    }
}