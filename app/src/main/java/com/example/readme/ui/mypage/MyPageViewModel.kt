package com.example.readme.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPageViewModel : ViewModel() {
    private val _profileName = MutableLiveData("Username")
    val profileName: LiveData<String> get() = _profileName

    private val _profileId = MutableLiveData("@testUserId")
    val profileId: LiveData<String> get() = _profileId

    private val _profileBio = MutableLiveData("This is the bio")
    val profileBio: LiveData<String> get() = _profileBio

    private val _followersCount = MutableLiveData(700)
    val followersCount: LiveData<Int> get() = _followersCount

    private val _followingCount = MutableLiveData(777)
    val followingCount: LiveData<Int> get() = _followingCount
}