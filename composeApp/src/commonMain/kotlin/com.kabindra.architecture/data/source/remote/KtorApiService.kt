package com.kabindra.architecture.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class KtorApiService(private val client: HttpClient) {
    suspend fun fetchNews(): HttpResponse {
        return client.get("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=5b229f0c81ac4134b7c2906971a65665")
    }
}
