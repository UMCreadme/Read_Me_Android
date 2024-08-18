package com.example.readme.ui.mypage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.readme.data.remote.ProfileUpdateRequest
import kotlinx.coroutines.launch

class EditMyPageViewModel(application: Application) : AndroidViewModel(application) {

    private val _profileEmail = MutableLiveData<String>()
    val profileEmail: LiveData<String> = _profileEmail

    private val _profileName = MutableLiveData<String>()
    val profileName: LiveData<String> = _profileName

    private val _profileAccount = MutableLiveData<String>()
    val profileAccount: LiveData<String> = _profileAccount

    private val _profileBio = MutableLiveData<String>()
    val profileBio: LiveData<String> = _profileBio

    private val _profileImg = MutableLiveData<String>()
    val profileImg: LiveData<String> = _profileImg

    fun setProfileEmail(email: String) {
        _profileEmail.value = email
    }

    fun setProfileName(name: String) {
        _profileName.value = name
    }

    fun setProfileAccount(id: String) {
        _profileAccount.value = id
    }

    fun setProfileBio(bio: String) {
        _profileBio.value = bio
    }

    fun setProfileImg(img: String) {
        _profileImg.value = img
    }

    fun getProfileUpdateRequest(): ProfileUpdateRequest {
        return ProfileUpdateRequest(
            nickname = _profileName.value,
            account = _profileAccount.value,
            comment = _profileBio.value
        )
    }

    fun saveProfileChanges() {
        // API를 통해 변경된 프로필 정보 저장
        viewModelScope.launch {
            try {
                // API 호출
                // 성공 시 처리
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}