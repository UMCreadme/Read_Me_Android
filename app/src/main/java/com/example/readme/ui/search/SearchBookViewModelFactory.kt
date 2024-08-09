package com.example.readme.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.remote.ReadmeServerService

class SearchBookViewModelFactory(
    private val token: String,
    private val apiService: ReadmeServerService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchBookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchBookViewModel(token, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}