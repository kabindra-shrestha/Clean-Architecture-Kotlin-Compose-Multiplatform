package com.kabindra.architecture.data.source.remote

import com.kabindra.architecture.data.model.NewsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorApiService(private val client: HttpClient) {
    suspend fun fetchNews(): NewsDto {
        return client.get("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=5b229f0c81ac4134b7c2906971a65665")
            .body<NewsDto>()
    }
}
