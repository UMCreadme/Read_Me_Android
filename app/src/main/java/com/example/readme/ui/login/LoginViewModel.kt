package com.example.readme.ui.login

import LoginRepository
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.KaKaoUser
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(application: Application):AndroidViewModel(application){
    private val repository = LoginRepository()
    val kakaoUserResponse = MutableLiveData<KakaoResponse>()

    fun sendKakaoUserInfo(user: KaKaoUser) {
        viewModelScope.launch {
            try {
                val response = LoginRepository.sendKakaoUserInfo(user)
                kakaoUserResponse.postValue(response)
            } catch (e: Exception) {
                Log.e(TAG, "Error sending Kakao user info", e)
            }
        }
    }
}