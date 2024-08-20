package com.example.readme.ui.community.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.SearchRepository

class RecentSelectBookViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentSelectBookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecentSelectBookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}