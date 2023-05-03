package com.example.openapi.blog.controller

import com.example.openapi.blog.dto.BlogDto
import com.example.openapi.blog.entity.Word
import com.example.openapi.blog.service.BlogService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/blog")
@RestController
class BlogController(
    val blogService: BlogService
) {

    @GetMapping("")
    fun search(@RequestBody blogDto: BlogDto): String? {
        val result: String? = blogService.searchKakao(blogDto)
        return result
    }

    @GetMapping("/rank")
    fun searchWordRank(): List<Word> = blogService.searchWordRank()
}