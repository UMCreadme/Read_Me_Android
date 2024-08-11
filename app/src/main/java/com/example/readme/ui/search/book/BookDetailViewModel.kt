package com.example.readme.ui.search.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.BookDetail
import com.example.readme.data.entities.ShortsPreview
import com.example.readme.data.repository.SearchRepository
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: SearchRepository
) : ViewModel() {
    private val TAG = BookDetailViewModel::class.java.simpleName
    private val _bookDetail = MutableLiveData<BookDetail>()
    val bookDetail: LiveData<BookDetail?> get() = _bookDetail

    private val _shorts = MutableLiveData<List<ShortsPreview>?>()
    private val shorts: LiveData<List<ShortsPreview>?> get() = _shorts

    fun getBookDetail(isbn: String) {
        viewModelScope.launch {
            try {
                val response = repository.getBookDetail(isbn)
                if (response.isSuccess) {
                    _bookDetail.postValue(response.result.bookDetail)
                    _shorts.postValue(response.result.shorts)
                } else {
                    Log.e(TAG, "Failed to fetch book detail: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching book detail", e)
            }
        }
    }

    fun getBookDetail(bookId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getBookDetail(bookId)
                if (response.isSuccess) {
                    _bookDetail.postValue(response.result.bookDetail)
                    _shorts.postValue(response.result.shorts)
                } else {
                    Log.e(TAG, "Failed to fetch book detail: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching book detail", e)
            }
        }
    }

    fun updateReadStatus(bookId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.updateReadStatus(bookId)
                if(response.code == "2010") {
                    _bookDetail.postValue(_bookDetail.value?.copy(isRead = true))
                } else if (response.code == "2040") {
                    _bookDetail.postValue(_bookDetail.value?.copy(isRead = false))
                }
                if (!response.isSuccess) {
                    Log.e(TAG, "Failed to update read status: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating read status", e)
            }
        }
    }

    fun updateReadStatus(isbn: String) {
        viewModelScope.launch {
            try {
                val response = repository.updateReadStatus(isbn)
                if (!response.isSuccess) {
                    Log.e(TAG, "Failed to update read status: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating read status", e)
            }
        }
    }
}