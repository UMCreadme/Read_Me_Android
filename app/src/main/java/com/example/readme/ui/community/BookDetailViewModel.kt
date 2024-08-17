package com.example.readme.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookDetailViewModel : ViewModel() {

    private val _bookTitle = MutableLiveData<String>().apply { value = "아무것도 없는 책" }
    val bookTitle: LiveData<String> = _bookTitle

    private val _bookAuthor = MutableLiveData<String>().apply { value = "레미 쿠르종 글/그림    이성엽 옮김" }
    val bookAuthor: LiveData<String> = _bookAuthor

    private val _userName = MutableLiveData<String>().apply { value = "kimtomato" }
    val userName: LiveData<String> = _userName

    private val _userLocation = MutableLiveData<String>().apply { value = "부산" }
    val userLocation: LiveData<String> = _userLocation

    private val _userComment = MutableLiveData<String>().apply { value = "저는 이 책을 읽고 어떤한 부분에서 설렜는데 비슷하게 설렌 분 찾습니다.\n\n누구든 좋아요~" }
    val userComment: LiveData<String> = _userComment

    private val _tags = MutableLiveData<List<String>>().apply { value = listOf("#집에 가고싶다", "#집에 가고싶다", "#집에 가고싶다", "#집에 가고싶다", "#집에 가고싶다", "#집에 가고싶다", "#집에 가고싶다", "#집에 가고싶다") }
    val tags: LiveData<List<String>> = _tags

    private val _memberCount = MutableLiveData<String>().apply { value = "2/6" }
    val memberCount: LiveData<String> = _memberCount
}
