package com.kabindra.architecture.data.repository.remote

import com.kabindra.architecture.data.model.toDomain
import com.kabindra.architecture.data.source.remote.NewsDataSource
import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.repository.remote.NewsRepository
import com.kabindra.architecture.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val newsDataSource: NewsDataSource) : NewsRepository {
    override suspend fun getNews(): Flow<NetworkResult<News>> = flow {
        try {
            val userDto = newsDataSource.getNews()
            emit(NetworkResult.Success(userDto.toDomain()))
        } catch (e: Exception) {
            println("ERROR: $e")
            emit(NetworkResult.Error(e))
        }
    }
}
