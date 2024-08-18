package com.example.readme.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.remote.ReadmeServerService

class UserProfileViewModelFactory(
    private val userId: Int,
    private val apiService: ReadmeServerService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserProfileViewModel(userId, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
