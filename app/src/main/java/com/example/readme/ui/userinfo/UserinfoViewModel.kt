package com.example.readme.ui.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserinfoViewModel :ViewModel(){
    companion object {
        const val MIN_NICKNAME_LENGTH = 12
        const val MIN_ID_LENGTH = 30
    }

    private val _nicknameError = MutableLiveData<String?>()
    val nicknameError: LiveData<String?> get() = _nicknameError

    private val _idError = MutableLiveData<String?>()
    val idError: LiveData<String?> get() = _idError

    fun validateNickname(nickname: String) {
        _nicknameError.value = if (nickname.length < MIN_NICKNAME_LENGTH) {
            "*12자 이상입니다."
        } else {
            null
        }
    }

    fun validateId(id: String) {
        _idError.value = if (id.length < MIN_ID_LENGTH) {
            "*30자 이상입니다."
        } else {
            null
        }
    }
}