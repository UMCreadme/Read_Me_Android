package com.example.readme.ui.community.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.recentbook.Book
import com.example.readme.data.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentSelectBookViewModel(
    private val repository: SearchRepository
) : ViewModel() {
    private val TAG = RecentSelectBookViewModel::class.java.simpleName
    private val _recentSelectBookItems = MutableLiveData<List<Book>?>()
    val recentSelectBookItems: LiveData<List<Book>?> get() = _recentSelectBookItems

    fun fetchRecentSelectBookItems(): LiveData<List<Book>?> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRecentSelectBooks()

                // 응답이 성공일 경우
                if(response.isSuccess){
                    val items = response.result
                    _recentSelectBookItems.postValue(items)
                } else {
                    Log.e(TAG, "Failed to fetch recent select book items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching recent select book items", e)
            }
        }

        return _recentSelectBookItems
    }
}