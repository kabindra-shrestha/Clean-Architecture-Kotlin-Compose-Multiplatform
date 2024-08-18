package com.kabindra.architecture.domain.usecase.remote

import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.repository.remote.NewsRepository
import kotlinx.coroutines.flow.Flow
import com.kabindra.architecture.utils.NetworkResult

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(): Flow<NetworkResult<News>> {
        return repository.getNews()
    }
}
