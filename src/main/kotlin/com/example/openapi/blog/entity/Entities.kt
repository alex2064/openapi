package com.example.openapi.blog.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Word(
    @Id val word: String,
    var cnt: Int = 0
)