package com.example.readme.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.SearchRepository

class RecentSearchViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecentSearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
