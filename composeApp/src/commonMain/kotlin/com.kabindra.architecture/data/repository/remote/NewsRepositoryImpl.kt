package com.kabindra.architecture.data.repository.remote

import com.kabindra.architecture.data.model.NewsDto
import com.kabindra.architecture.data.model.toDomain
import com.kabindra.architecture.data.source.remote.NewsDataSource
import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.repository.remote.NewsRepository
import com.kabindra.architecture.utils.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val newsDataSource: NewsDataSource) : NewsRepository {
    override suspend fun getNews(): Flow<Result<News>> = flow {
        emit(Result.Loading)
        try {
            val response: HttpResponse = newsDataSource.getNews()
            if (response.status.isSuccess()) {
                val responses: NewsDto = response.body()

                emit(Result.Success(responses.toDomain()))
            } else {
                emit(Result.Error("Error: ${response.status}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Error: Exception: ${e.message}", e))
        }
    }
}
