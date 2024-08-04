package data.source.remote

import data.model.NewsDto

class NewsDataSource(private val apiService: KtorApiService) {
    suspend fun getNews(): NewsDto {
        return apiService.fetchNews()
    }
}
