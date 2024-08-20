package com.example.readme.data.entities

import com.example.readme.ui.community.Book

data class BookResponse(
    val books: List<Book>
)

data class Book(
    val id: Int,
    val title: String,
    val author: String
)
