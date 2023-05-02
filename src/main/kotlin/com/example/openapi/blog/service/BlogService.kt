package com.example.openapi.blog.service

import com.example.openapi.blog.dto.BlogDto
import com.example.openapi.blog.entity.Word
import com.example.openapi.blog.repository.WordRepository
import com.example.openapi.core.exception.InvalidInputException
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BlogService(
    val wordRepository: WordRepository
) {
    fun selectKakao(blogDto: BlogDto): String? {

        var message: StringBuilder = StringBuilder()

        if (blogDto.query.trim().isEmpty()) {
            message.append(", query parameter required")
        }

        if (blogDto.sort.trim() !in arrayOf("accuracy","recency")) {
            message.append(", sort parameter one of accuracy and recency")
        }

        when {
            blogDto.page < 1 -> message.append(", page is less than min")
            blogDto.page > 50 -> message.append(", page is more than max")
        }

        if (message.isNotEmpty()) {
            val exceptionMessage = message.delete(0, 2).toString()
            throw InvalidInputException(exceptionMessage)
        }

        val webClient: WebClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webClient
            .get()
            .uri { it.path("/v2/search/blog")
                    .queryParam("query", blogDto.query)
                    .queryParam("sort", blogDto.sort)
                    .queryParam("page", blogDto.page)
                    .queryParam("size", blogDto.size)
                    .build() }
            .header("Authorization", "KakaoAK ac5d9a371616d5c1a4c00e6e81230a6e")
            .retrieve()
            .bodyToMono<String>()

        val result = response.block()

        val lowQuery: String = blogDto.query.lowercase()
        val word: Word = wordRepository.findById(lowQuery).orElse(Word(lowQuery))
        word.cnt++

        wordRepository.save(word)

        return result
    }

    fun selectWordRank(): List<Word> = wordRepository.findTop10ByOrderByCntDesc()
}
