package com.example.readme.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.remote.ProfileResponse
import com.example.readme.data.remote.ReadmeServerService
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userId: String, private val apiService: ReadmeServerService) : ViewModel() {

    private val _profileName = MutableLiveData<String>()
    val profileName: LiveData<String> get() = _profileName

    private val _profileId = MutableLiveData<String>()
    val profileId: LiveData<String> get() = _profileId

    private val _profileBio = MutableLiveData<String>()
    val profileBio: LiveData<String> get() = _profileBio

    private val _readCount = MutableLiveData<Int>()
    val readCount: LiveData<Int> get() = _readCount

    private val _followersCount = MutableLiveData<Int>()
    val followersCount: LiveData<Int> get() = _followersCount

    private val _followingCount = MutableLiveData<Int>()
    val followingCount: LiveData<Int> get() = _followingCount

    fun getProfile(userId: String): LiveData<ProfileResponse> {
        val result = MutableLiveData<ProfileResponse>()
        viewModelScope.launch {
            try {
                val response = apiService.getProfile(userId)
                /*if (response.isSuccessful) {
                    response.body()?.let {
                        _profileName.value = it.name
                        _profileId.value = it.id
                        _profileBio.value = it.bio
                        _readCount.value = it.readCount
                        _followersCount.value = it.followersCount
                        _followingCount.value = it.followingCount
                        result.value = it
                    }
                }*/
            } catch (e: Exception) {
                // Handle error
            }
        }
        return result
    }
}
