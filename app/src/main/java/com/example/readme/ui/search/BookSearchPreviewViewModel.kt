package com.example.readme.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readme.data.entities.BookInfo
import com.example.readme.data.remote.ReadmeServerService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookSearchPreviewViewModel(private val token: String, private val apiService: ReadmeServerService) : ViewModel() {
    private val TAG = BookSearchPreviewViewModel::class.java.simpleName
    private val _searchBookItems = MutableLiveData<List<BookInfo>?>()
    val searchBookItems: MutableLiveData<List<BookInfo>?> get() = _searchBookItems

    // MutableStateFlow to hold the search query
    private val queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(300) // 300ms debounce time to prevent excessive API calls
                .distinctUntilChanged() // Only proceed if the query actually changes
                .filter { it.isNotBlank() } // Ignore empty queries
                .collect { query ->
                    searchBook(query)
                }
        }
    }

    fun onQueryTextChange(newQuery: String) {
        queryFlow.value = newQuery
    }

    // 책 검색
    fun searchBook(query: String): MutableLiveData<List<BookInfo>?> {
        viewModelScope.launch {
            try {
                // Retrofit API 호출
                val response = apiService.searchBooksPreview("Bearer $token", query, 1, 50)
                Log.i(TAG, "response: $response")

                // 응답이 성공일 경우
                if(response.isSuccess){
                    val items = response.result
                    _searchBookItems.postValue(items)
                } else {
                    // 실패한 경우
                    Log.e(TAG, "Failed to fetch search book items: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                // 예외 발생한 경우
                Log.e(TAG, "Error fetching search book items", e)
            }
        }

        return _searchBookItems
    }
}
