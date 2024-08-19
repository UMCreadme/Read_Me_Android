package com.example.readme.ui.mypage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditMyPageViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMyPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditMyPageViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}