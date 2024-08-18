package com.kabindra.architecture.domain.repository.remote

import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<NetworkResult<News>>
}
