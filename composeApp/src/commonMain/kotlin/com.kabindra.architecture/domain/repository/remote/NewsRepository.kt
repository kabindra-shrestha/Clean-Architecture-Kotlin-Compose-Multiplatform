package com.kabindra.architecture.domain.repository.remote

import com.kabindra.architecture.domain.entity.News
import kotlinx.coroutines.flow.Flow
import com.kabindra.architecture.utils.NetworkResult

interface NewsRepository {
    suspend fun getNews(): Flow<NetworkResult<News>>
}
