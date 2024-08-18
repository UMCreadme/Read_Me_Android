package com.example.readme.ui.search.book

import android.util.Log
import android.widget.Toast
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
    val shorts: LiveData<List<ShortsPreview>?> get() = _shorts

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> get() = _toastMessage

    // 페이지네이션 관련 변수들
    private var currentPage = 1
    private var hasNext = true
    private var isLoading = false
    private val SIZE = 30

    fun getBookDetail(bookId: Int, page: Int = 1) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.getBookDetail(bookId, page, SIZE)
                if (response.isSuccess) {
                    if (page == 1) {
                        _bookDetail.postValue(response.result.bookDetail)
                        _shorts.postValue(response.result.shorts)
                    } else {
                        val currentList = _shorts.value.orEmpty().toMutableList()
                        currentList.addAll(response.result.shorts)
                        _shorts.postValue(currentList)
                    }
                    hasNext = response.pageInfo.hasNext
                    currentPage = page
                } else {
                    Log.e(TAG, "Failed to fetch book detail: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching book detail", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun getBookDetail(ISBN: String, page: Int = 1) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.getBookDetail(ISBN, page, SIZE)
                if (response.isSuccess) {
                    if (page == 1) {
                        _bookDetail.postValue(response.result.bookDetail)
                        _shorts.postValue(response.result.shorts)
                    } else {
                        val currentList = _shorts.value.orEmpty().toMutableList()
                        currentList.addAll(response.result.shorts)
                        _shorts.postValue(currentList)
                    }
                    hasNext = response.pageInfo.hasNext
                    currentPage = page
                } else {
                    Log.e(TAG, "Failed to fetch book detail: ${response.code} - ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching book detail", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreShorts() {
        if (isLoading || !hasNext) return
        val bookId = _bookDetail.value?.bookId ?: return
        getBookDetail(bookId, currentPage + 1)
    }

    fun updateReadStatus(bookId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.updateReadStatus(bookId)
                if(response.code == "COMMON002") {
                    // 읽음 업데이트 권한 없음 알림 표시
                    _toastMessage.value = Event("로그인이 필요한 서비스입니다.")
                    _bookDetail.postValue(_bookDetail.value?.copy(isRead = false))
                }
                else if(response.code == "2010") {
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
                if(response.code == "COMMON002") {
                    // 읽음 업데이트 권한 없음 알림 표시
                    _toastMessage.value = Event("로그인이 필요한 서비스입니다.")
                    _bookDetail.postValue(_bookDetail.value?.copy(isRead = false))
                }
                else if(response.code == "2010") {
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
}
