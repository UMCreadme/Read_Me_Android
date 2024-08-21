package com.example.readme.ui.mypage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.readme.data.remote.ProfileUpdateRequest
import com.example.readme.data.remote.ReadmeServerService
import com.example.readme.utils.RetrofitClient
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

    private val apiService: ReadmeServerService = RetrofitClient.getReadmeServerService()

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

    fun saveProfileChanges(token: String) {
        viewModelScope.launch {
            try {
                val profileUpdateRequest = getProfileUpdateRequest()
                val response = apiService.updateMyProfile(token, profileUpdateRequest)

                if (response.isSuccess) {
                    Log.d("EditMyPageViewModel", "updating isSuccess")
                }
            } catch (e: Exception) {
                Log.e("EditMyPageViewModel", "Error updating profile", e)
            }
        }
    }
}