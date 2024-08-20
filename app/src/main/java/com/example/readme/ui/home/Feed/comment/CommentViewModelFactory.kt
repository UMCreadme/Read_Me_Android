package com.example.readme.ui.home.Feed.comment
import CommentListService
import CommentViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readme.ui.login.LoginViewModel

class CommentViewModelFactory(private val commentListService: CommentListService)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(commentListService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
