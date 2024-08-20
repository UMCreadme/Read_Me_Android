package com.example.readme.ui.home.Feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.entities.category.CategoryFeedResponse
import com.example.readme.data.entities.category.FeedInfo
import com.example.readme.data.entities.inithome.MainInfoResponse
import com.example.readme.data.entities.inithome.ShortsInfo
import com.example.readme.utils.RetrofitClient
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

// ViewModel에서 데이터 요청
class FeedViewModel : ViewModel() {
    private val _feeds = MutableLiveData<List<com.example.readme.data.entities.inithome.FeedInfo>>()
    val feeds: LiveData<List<com.example.readme.data.entities.inithome.FeedInfo>> get() = _feeds

    private val _shorts = MutableLiveData<List<ShortsInfo>>()
    val shorts: LiveData<List<ShortsInfo>> get() = _shorts

    private val _categoryFeeds = MutableLiveData<List<FeedInfo>>()
    val categoryFeeds: LiveData<List<FeedInfo>> get() = _categoryFeeds


    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _combinedData = MediatorLiveData<Pair<List<com.example.readme.data.entities.inithome.FeedInfo>, List<ShortsInfo>>>()
    val combinedData: LiveData<Pair<List<com.example.readme.data.entities.inithome.FeedInfo>, List<ShortsInfo>>> get() = _combinedData

    init {
        _combinedData.value = Pair(emptyList(), emptyList())

        _combinedData.addSource(feeds) { feeds ->
            val shorts = _shorts.value
            _combinedData.value = Pair(feeds ?: emptyList(), shorts ?: emptyList())
        }
        _combinedData.addSource(shorts) { shorts ->
            val feeds = _feeds.value
            _combinedData.value = Pair(feeds ?: emptyList(), shorts ?: emptyList())
        }
    }


    fun fetchFeeds() {
        RetrofitClient.getMainInfoService().getMainInfo().enqueue(object : Callback<MainInfoResponse> {
            override fun onResponse(call: Call<MainInfoResponse>, response: Response<MainInfoResponse>) {
                if (response.body()?.isSuccess == true) {
                    val result = response.body()?.result
                    // 로그 추가
                    Log.d("FeedViewModel", "Fetched feeds: ${result?.feeds}")

                    // 서버에서 받아온 카테고리 정보를 LiveData에 저장
                    val categories = result?.categories ?: emptyList()
                    _categories.postValue(categories)

                    // 필터링 없이 전체 feeds 리스트를 사용
                    _feeds.postValue(result?.feeds ?: emptyList())
                    Log.d("FeedViewModel", "Feeds posted to LiveData: ${_feeds.value}")

                    _shorts.postValue(result?.shorts ?: emptyList())
                    Log.d("FeedViewModel", "shorts posted to LiveData: ${_shorts.value}")

                } else {
                    // 오류 처리
                    Log.d("FeedViewModel", "Response not successful")
                }
            }

            override fun onFailure(call: Call<MainInfoResponse>, t: Throwable) {
                // 오류 처리
                Log.d("FeedViewModel", "Failed to fetch data: ${t.message}")
            }
        })
    }

    fun fetchCategoryFeeds(category: String) {
        RetrofitClient.getMainInfoService().getCategoryFeeds(1, 20, category).enqueue(object : Callback<CategoryFeedResponse> {
            override fun onResponse(call: Call<CategoryFeedResponse>, response: Response<CategoryFeedResponse>) {
                Log.d("anothor", "fetchCatrgory")
                if (response.body()?.isSuccess == true) {
                    val feedList: List<FeedInfo> = response.body()?.result ?: emptyList()
                    _categoryFeeds.postValue(feedList)
                    Log.d("anothor", "${feedList}")
                } else {
                    Log.d("FeedViewModel", "Response not successful")
                }
            }

            override fun onFailure(call: Call<CategoryFeedResponse>, t: Throwable) {
                // 오류 처리
                Log.d("FeedApi", "통신 실패: ${t.message}")
            }
        })
    }
}

