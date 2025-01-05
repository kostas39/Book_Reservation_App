package com.example.book_reservation_app

import java.io.Serializable

data class Book(
    val bookName: String,
    val bookImage: String,
    val bookAuthors: String,
    val bookPublisher: String
) : Serializable

