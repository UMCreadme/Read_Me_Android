package com.example.readme.ui.community.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.CommunityRepository

class CommunityMyViewModelFactory(
    private val repository: CommunityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityMyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommunityMyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}