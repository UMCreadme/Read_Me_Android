package com.example.readme.ui.search.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.data.repository.SearchRepository

class BookDetailViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}