package com.example.openapi.blog.repository

import com.example.openapi.blog.entity.Word
import org.springframework.data.jpa.repository.JpaRepository

interface WordRepository : JpaRepository<Word, String> {
    fun findTop10ByOrderByCntDesc(): List<Word>
}