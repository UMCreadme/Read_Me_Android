package com.example.readme.ui.home.make.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readme.data.entities.booklist.Book
import com.example.readme.data.entities.booklist.BookListResponse
import com.example.readme.data.entities.recentbook.RecentBookResponse
import com.example.readme.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel : ViewModel() {

    private val _recentBooks = MutableLiveData<List<com.example.readme.data.entities.recentbook.Book>>()
    val recentBooks: LiveData<List<com.example.readme.data.entities.recentbook.Book>> get() = _recentBooks

    private val _BookList = MutableLiveData<List<Book>>()
    val BookList: LiveData<List<Book>> get() = _BookList

    fun fetchRecentBooks() {
        RetrofitClient.getMainInfoService().getRecentBooks().enqueue(object : Callback<RecentBookResponse> {
            override fun onResponse(call: Call<RecentBookResponse>, response: Response<RecentBookResponse>) {
                if (response.body()?.isSuccess == true) {
                    val bookList = response.body()?.result ?: emptyList()
                    _recentBooks.postValue(bookList)
                    Log.d("BookViewModel", "Fetched recent books: $bookList")
                } else {
                    Log.d("BookViewModel", "Response not successful")
                }
            }

            override fun onFailure(call: Call<RecentBookResponse>, t: Throwable) {
                Log.d("BookViewModel", "Failed to fetch recent books: ${t.message}")
            }
        })
    }


    fun fetchBookList(keyword: String) {
        RetrofitClient.getMainInfoService().getBookList(keyword).enqueue(object : Callback<BookListResponse> {
            override fun onResponse(call: Call<BookListResponse>, response: Response<BookListResponse>) {

                if (response.body()?.isSuccess == true) {
                    val bookList = response.body()?.result ?: emptyList()
                    _BookList.postValue(bookList)

                } else {
                    Log.d("BookViewModel", "Response not successful")
                }
            }

            override fun onFailure(call: Call<BookListResponse>, t: Throwable) {
                Log.d("BookViewModel", "Failed to fetch  books List: ${t.message}")
            }
        })
    }
}
