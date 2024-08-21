package com.example.readme.ui.userinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.LoginResult
import com.example.readme.data.entities.UserinfoResponse
import com.example.readme.data.entities.UserData
import com.example.readme.data.remote.Response
import com.example.readme.data.remote.ResponseWithData
import com.example.readme.data.repository.LoginRepository
import com.example.readme.utils.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserinfoViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _signResponse = MutableLiveData<ResponseWithData<LoginResult?>?>()
    val signResponse: LiveData<ResponseWithData<LoginResult?>?> get() = _signResponse

    private val _member4005Error = MutableLiveData<Boolean>()
    val member4005Error: LiveData<Boolean> get() = _member4005Error

    fun sendSignUpInfo(user: UserData) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.sendSignUpInfo(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccess && response.result != null) {
                        // 액세스 토큰 설정
                        RetrofitClient.setToken(response.result.accessToken)
                        _signResponse.postValue(response)
                    }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorBodyParsing = errorBody?.let {
                    Gson().fromJson(it, Response::class.java)  // Gson으로 파싱
                }

                if (errorBodyParsing != null) {
                    if(errorBodyParsing.code == "MEMBER4005") {
                        _member4005Error.postValue(true)
                    }
                }
            } catch (e: Exception) {
                Log.e("UserinfoViewModel", "Error sending sign up info", e)
            }
        }
    }
}
