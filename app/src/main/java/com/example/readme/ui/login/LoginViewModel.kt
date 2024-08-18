package com.example.readme.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.KaKaoUser
import com.example.readme.data.entities.UserData
import com.example.readme.utils.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LoginRepository()
    val kakaoUserResponse = MutableLiveData<LoginResponse>()

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData
   // private var retrofitClient: Retrofit? = null


    fun sendKakaoUserInfo(user: KaKaoUser) {
        viewModelScope.launch {
            try {
                val response = repository.sendKakaoUserInfo(user)
                Log.d("LoginViewModel", "Login request: $user")
                Log.d("LoginViewModel", "Login response: ${response.body()}")
                Log.d("accessTocken","accessTocken: ${response.body()!!.result!!.accessToken}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        kakaoUserResponse.postValue(response.body())
                        val newUserData = UserData(
                            uniqueId = user.id ?: "",
                            email = user.email ?: "",
                            nickname = "",
                            account = "",
                            categoryIdList = listOf()
                        )
                        Log.d("LoginViewModel", "New UserData: $newUserData")
                        _userData.postValue(newUserData)

                        RetrofitClient.setToken(responseBody.result!!.accessToken)

                    } else {
                        Log.e("LoginViewModel", "Response body is null")
                    }
                } else {
                    Log.e("LoginViewModel", "Response is not successful: ${response.code()}")
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginViewModel", "Error body: $errorBody")

                    // 에러 응답을 파싱하여 처리
                    val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                    kakaoUserResponse.postValue(errorResponse)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error sending Kakao user info", e)
            }
        }
    }

    fun sendSignUpInfo(user: UserData) {
        viewModelScope.launch {
            try {
                val response = repository.sendSignUpInfo(user)
                Log.d("LoginViewModel", "Sign up request: $user")
                Log.d("LoginViewModel", "Sign up response: ${response.body()}")
                if (response.isSuccessful) {
                    kakaoUserResponse.postValue(response.body())
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