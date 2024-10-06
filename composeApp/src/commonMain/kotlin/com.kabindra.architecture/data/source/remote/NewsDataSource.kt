package com.kabindra.architecture.data.source.remote

import io.ktor.client.statement.HttpResponse

class NewsDataSource(private val apiService: KtorApiService) {
    suspend fun getNews(): HttpResponse {
        return apiService.fetchNews()
    }
}
