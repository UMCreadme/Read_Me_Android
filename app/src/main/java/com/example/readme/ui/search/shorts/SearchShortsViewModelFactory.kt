package com.example.readme.ui.search.shorts

import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.SearchRepository

class SearchShortsViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchShortsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchShortsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}