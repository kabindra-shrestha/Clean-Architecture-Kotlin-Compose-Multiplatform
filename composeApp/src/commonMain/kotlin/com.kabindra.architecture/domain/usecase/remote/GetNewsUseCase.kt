package com.kabindra.architecture.domain.usecase.remote

import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.repository.remote.NewsRepository
import com.kabindra.architecture.utils.Result
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(): Flow<Result<News>> {
        return repository.getNews()
    }
}
