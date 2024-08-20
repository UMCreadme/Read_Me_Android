package com.example.readme.ui.home.shortsdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
}