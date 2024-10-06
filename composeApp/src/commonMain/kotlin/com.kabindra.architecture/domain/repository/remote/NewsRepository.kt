package com.kabindra.architecture.domain.repository.remote

import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.utils.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<Result<News>>
}
