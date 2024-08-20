package com.example.readme.ui.home.make.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.booklist.Book
import com.example.readme.data.entities.booklist.BookListResponse
import com.example.readme.data.entities.recentbook.RecentBookResponse
import com.example.readme.utils.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel : ViewModel() {

    private val _recentBooks = MutableLiveData<List<com.example.readme.data.entities.recentbook.Book>>()
    val recentBooks: LiveData<List<com.example.readme.data.entities.recentbook.Book>> get() = _recentBooks

    private val _BookList = MutableLiveData<List<Book>>()
    val BookList: LiveData<List<Book>> get() = _BookList

    fun fetchRecentBooks() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.getMainInfoService().getRecentBooks()

                if (response.body()?.isSuccess == true) {
                    val bookList = response.body()?.result ?: emptyList()
                    _recentBooks.postValue(bookList)
                    Log.d("BookViewModel", "Fetched recent books: $bookList")
                } else {
                    Log.d("BookViewModel", "Response not successful")
                }
            } catch (e: Exception) {
                Log.d("BookViewModel", "Failed to fetch recent books: ${e.message}")
            }
        }
    }

    // 도서 목록 검색 (코루틴 사용)
    fun fetchBookList(keyword: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.getMainInfoService().getBookList(keyword)

                if (response.body()?.isSuccess == true) {
                    val bookList = response.body()?.result ?: emptyList()
                    _BookList.postValue(bookList)
                    Log.d("BookViewModel", "Fetched books: $bookList")
                } else {
                    Log.d("BookViewModel", "Response not successful")
                }
            } catch (e: Exception) {
                Log.d("BookViewModel", "Failed to fetch books list: ${e.message}")
            }
        }
    }
}