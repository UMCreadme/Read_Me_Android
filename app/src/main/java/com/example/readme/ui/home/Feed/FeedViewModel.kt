package com.example.readme.ui.home.Feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.inithome.FeedInfo
import com.example.readme.data.entities.inithome.ShortsInfo
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// ViewModel에서 데이터 요청
class FeedViewModel : ViewModel() {

    private val _shorts = MutableLiveData<List<ShortsInfo>>()
    val shorts: LiveData<List<ShortsInfo>> get() = _shorts

    private val _feedsShorts = MutableLiveData<List<FeedInfo>>()
    val feedsShorts: LiveData<List<FeedInfo>> get() = _feedsShorts

    private val _categoryFeeds = MutableLiveData<List<com.example.readme.data.entities.category.FeedInfo>>()
    val categoryFeeds: LiveData<List<com.example.readme.data.entities.category.FeedInfo>> get() = _categoryFeeds


    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _combinedData = MediatorLiveData<Pair<List<FeedInfo>, List<ShortsInfo>>>()
    val combinedData: LiveData<Pair<List<FeedInfo>, List<ShortsInfo>>> get() = _combinedData

    init {
        _combinedData.value = Pair(emptyList(), emptyList())

        _combinedData.addSource(feedsShorts) { feedsShorts ->
            val shorts = _shorts.value
            _combinedData.value = Pair(feedsShorts ?: emptyList(), shorts ?: emptyList())
        }
        _combinedData.addSource(shorts) { shorts ->
            val feedsShorts = _feedsShorts.value
            _combinedData.value = Pair(feedsShorts ?: emptyList(), shorts ?: emptyList())
        }
    }


    fun fetchFeeds() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getMainInfoService().getMainInfo()
                }
                if (response.body()?.isSuccess == true) {
                    val result = response.body()?.result
//                    Log.d("FeedViewModel", "Fetched feeds: ${result}")
                    val categories = result?.categories ?: emptyList()
                    _categories.postValue(categories)
                    val feedsShorts = result?.feeds ?: emptyList()
//                    Log.d("FeedViewModel", "Fetched feeds: ${feedsShorts}")
                    _feedsShorts.postValue(feedsShorts)
                    Log.d("FeedViewModel", "Fetched _feedsShorts: ${feedsShorts}")
                    val shorts = result?.shorts ?: emptyList()
                    _shorts.postValue(shorts)
                } else {
                    Log.d("FeedViewModel", "Response not successful")
                }
            } catch (e: Exception) {
                Log.d("FeedViewModel", "Failed to fetch data: ${e.message}")
            }
        }
    }

    fun fetchCategoryFeeds(category: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getMainInfoService().getCategoryFeeds(1, 10, category)
                }
                if (response.body()?.isSuccess == true) {
                    val feedList = response.body()?.result ?: emptyList()
                    _categoryFeeds.postValue(feedList)
//                    Log.d("anothor", "${feedList}")
                } else {
                    Log.d("FeedViewModel", "Response not successful")
                }
            } catch (e: Exception) {
                Log.d("FeedViewModel", "Failed to fetch data: ${e.message}")
            }
        }
    }

    fun likeShorts(feed: FeedInfo) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getMainInfoService().likeShorts(feed.shortsId)
                }
                if (response.body()?.isSuccess == true) {
                    val updatedFeeds = _feedsShorts.value?.map {
                        if (it.shortsId == feed.shortsId) {
                            it.copy(isLike = !it.isLike, likeCnt = response.body()?.result ?: it.likeCnt)
                        } else {
                            it
                        }
                    } ?: emptyList()
                    _feedsShorts.value = updatedFeeds
//                    Log.d("FeedViewModel", "Like updated for feed: ${feed.shortsId}")
                } else {
                    Log.d("FeedViewModel", "Like update failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.d("FeedViewModel", "Failed to update like status: ${e.message}")
            }
        }
    }

    fun likeShorts2(categoryFeeds: com.example.readme.data.entities.category.FeedInfo) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.getMainInfoService().likeShorts(categoryFeeds.shortsId)
                }
                if (response.body()?.isSuccess == true) {
                    val updatedFeeds = _categoryFeeds.value?.map {
                        if (it.shortsId == categoryFeeds.shortsId) {
                            it.copy(isLike = !it.isLike, likeCnt = response.body()?.result ?: it.likeCnt)
                        } else {
                            it
                        }
                    } ?: emptyList()
                    _categoryFeeds.value = updatedFeeds
//                    Log.d("FeedViewModel", "Like updated for feed2: ${categoryFeeds.shortsId}")
                } else {
                    Log.d("FeedViewModel", "Like update failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.d("FeedViewModel", "Failed to update like status: ${e.message}")
            }
        }
    }

    // 좋아요 상태를 업데이트하고, 필요한 경우 데이터를 다시 불러오는 함수
    fun updateLikeStatus(item: FeedInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            // 현재 searchShortsItems의 item에 해당하는 부분 업데이트
            val updatedItems = _feedsShorts.value.orEmpty().map {
                if (it.shortsId == item.shortsId) {
                    it.copy(isLike = item.isLike, likeCnt = item.likeCnt)
                } else {
                    it
                }
            }
            _feedsShorts.postValue(updatedItems)
        }
    }

    // 좋아요 상태를 업데이트하고, 필요한 경우 데이터를 다시 불러오는 함수
    fun updateLikeStatus2(item: com.example.readme.data.entities.category.FeedInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            // 현재 searchShortsItems의 item에 해당하는 부분 업데이트
            val updatedItems = _categoryFeeds.value.orEmpty().map {
                if (it.shortsId == item.shortsId) {
                    it.copy(isLike = item.isLike, likeCnt = item.likeCnt)
                } else {
                    it
                }
            }
            _categoryFeeds.postValue(updatedItems)
        }
    }
}
