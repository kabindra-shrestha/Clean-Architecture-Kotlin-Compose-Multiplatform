package domain.repository.remote

import domain.entity.News
import kotlinx.coroutines.flow.Flow
import utils.NetworkResult

interface NewsRepository {
    suspend fun getNews(): Flow<NetworkResult<News>>
}
