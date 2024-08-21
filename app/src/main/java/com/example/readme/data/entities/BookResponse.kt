package com.example.readme.data.entities

data class BookResponse(
    val books: List<Book>
)

data class Book(
    val id: Int,
    val title: String,
    val author: String
)
