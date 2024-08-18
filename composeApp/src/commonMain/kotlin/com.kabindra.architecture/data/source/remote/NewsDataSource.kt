package com.kabindra.architecture.data.source.remote

import com.kabindra.architecture.data.model.NewsDto

class NewsDataSource(private val apiService: KtorApiService) {
    suspend fun getNews(): NewsDto {
        return apiService.fetchNews()
    }
}
