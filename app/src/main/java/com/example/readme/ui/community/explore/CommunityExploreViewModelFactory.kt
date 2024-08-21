package com.example.readme.ui.community.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.CommunityRepository

class CommunityExploreViewModelFactory(
    private val repository: CommunityRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityExploreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommunityExploreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}