package com.example.openapi.blog.dto

data class BlogDto(
    val query: String,
    val sort: String = "accuracy",
    val page: Int = 1,
    val size: Int = 10
)