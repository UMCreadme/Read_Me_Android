package com.example.readme.ui.community.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.CommunityRepository

class CommunityCreateViewModelFactory(private val repository: CommunityRepository): ViewModelProvider.Factory {
    //전달 인자로 받기. 클래스내ㅜ파라미터

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityCreateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommunityCreateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}