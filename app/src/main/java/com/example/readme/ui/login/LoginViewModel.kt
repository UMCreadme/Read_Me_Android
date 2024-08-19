package com.example.readme.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.LoginResult
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.entities.UserData
import com.example.readme.data.remote.Response
import com.example.readme.data.repository.LoginRepository
import com.example.readme.utils.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {
    private val _kakaoUserResponse = MutableLiveData<LoginResult?>()
    val kakaoUserResponse: LiveData<LoginResult?> get() = _kakaoUserResponse

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    private val _isSignedUp = MutableLiveData<Boolean>()
    val isSignedUp: LiveData<Boolean> get() = _isSignedUp

    // 로그인 요청
    fun sendKakaoUserInfo(user: KaKaoUser) {
        viewModelScope.launch {
            try {
                val response = repository.sendKakaoUserInfo(user)

                if (response.isSuccess) {
                    _kakaoUserResponse.postValue(response.result)
                    val newUserData = UserData(
                        uniqueId = user.id ?: "",
                        email = user.email ?: "",
                        nickname = "",
                        account = "",
                        categoryIdList = listOf()
                    )
                    Log.d("LoginViewModel", "New UserData: $newUserData")
                    _userData.postValue(newUserData)

                    RetrofitClient.setToken(response.result.accessToken)
                } else {
                    Log.e("LoginViewModel", "Failed to send Kakao user info: ${response.code} - ${response.message}")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorBodyParsing = errorBody?.let {
                    Gson().fromJson(it, Response::class.java)  // Gson으로 파싱
                }

                if (errorBodyParsing != null) {
                    if(errorBodyParsing.code == "MEMBER4001") {
                        _isSignedUp.postValue(false)
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error sending Kakao user info", e)
            }
        }
    }

    // 회원가입 요청
    fun sendSignUpInfo(user: UserData) {
        viewModelScope.launch {
            try {
                val response = repository.sendSignUpInfo(user)
                Log.d("LoginViewModel", "Sign up request: $user")
                Log.d("LoginViewModel", "Sign up response: ${response}")
                if (response.isSuccess) {
                    _kakaoUserResponse.postValue(response.result)
                } else {
                    Log.e("LoginViewModel", "Failed to send sign up info: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error sending sign up info", e)
            }
        }
    }

    fun setNickname(nickname: String) {
        _userData.value = _userData.value?.copy(nickname = nickname) ?: UserData(
            uniqueId = "",
            email = "",
            nickname = nickname,
            account = "",
            categoryIdList = listOf()
        )
    }

    fun setAccount(account: String) {
        _userData.value = _userData.value?.copy(account = account) ?: UserData(
            uniqueId = "",
            email = "",
            nickname = "",
            account = account,
            categoryIdList = listOf()
        )
    }

    fun setCategoryIdList(categoryIdList: List<Int>) {
        _userData.value = _userData.value?.copy(categoryIdList = categoryIdList) ?: UserData(
            uniqueId = "",
            email = "",
            nickname = "",
            account = "",
            categoryIdList = categoryIdList
        )
    }

    fun updateUserData() {
        _userData.value?.let { sendSignUpInfo(it) }
    }
}