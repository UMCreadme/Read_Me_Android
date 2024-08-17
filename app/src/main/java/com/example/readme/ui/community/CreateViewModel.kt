package com.example.readme.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateViewModel : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    init {
        _books.value = getBookList()
    }

    fun filterBooks(query: String) {
        val filteredList = if (query.isEmpty()) {
            getBookList()
        } else {
            getBookList().filter {
                it.title.contains(query, ignoreCase = true) || it.author.contains(query, ignoreCase = true)
            }
        }
        _books.value = filteredList
    }

    private fun getBookList(): List<Book> {
        return listOf(
            Book("소년이 온다", "한강"),
            Book("우리가 빛의 속도로 갈 수 없다면", "김초엽"),
            Book("지구 끝의 온실", "김초엽"),
            Book("아주 희미한 빛으로도", "최은영"),
            Book("상실의 시대", "무라카미 하루키"),
            Book("작별인사", "김영하"),
            Book("인간 실격", "다자이 오사무"),
            Book("아버지의 해방일지", "정지아")
        )
    }
}