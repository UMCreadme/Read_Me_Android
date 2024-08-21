package com.example.readme.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.readme.data.remote.ProfileResponse
import com.example.readme.data.remote.ProfileShortsResponse
import com.example.readme.data.remote.ReadmeServerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileViewModel(private val apiService: ReadmeServerService) : ViewModel() {

    // 1. 프로필 정보
    private val _profile = MutableLiveData<ProfileResponse>()
    val profile: LiveData<ProfileResponse> get() = _profile

    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> get() = _isFollowing


    // 프로필 정보를 가져오는 함수
    fun fetchProfile(userId: Int): LiveData<ProfileResponse> {
        viewModelScope.launch {
            try {
                val response = apiService.getProfile(userId)

                // 응답 성공
                if (response.isSuccess) {
                    val profileResponse = response

                    // profileResponse가 null이 아닌 경우에만 처리
                    profileResponse?.let {
                        _profile.postValue(it)
                    } ?: run {
                        Log.e("UserProfileViewModel", "Profile response body is null")
                    }
                } else {
                    Log.e("UserProfileViewModel_fetch_profile", "Error: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("UserProfileViewModel_fetch_profile", "Exception: ${e.message}")
            }
        }
        return profile
    }

    fun followUser(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.followUser(token, userId)
                if (response.isSuccess) {
                    _isFollowing.value = true
                } else {
                    _isFollowing.value = false
                }
            } catch (e: Exception) {
                _isFollowing.value = false
            }
        }
    }

    fun unfollowUser(token: String, userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.unfollowUser(token, userId)
                if (response.isSuccess) {
                    _isFollowing.value = false
                } else {
                    _isFollowing.value = true
                }
            } catch (e: Exception) {
                _isFollowing.value = true
            }
        }
    }
}
