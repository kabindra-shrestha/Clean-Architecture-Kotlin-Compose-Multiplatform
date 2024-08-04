package domain.usecase.remote

import domain.entity.News
import domain.repository.remote.NewsRepository
import kotlinx.coroutines.flow.Flow
import utils.NetworkResult

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(): Flow<NetworkResult<News>> {
        return repository.getNews()
    }
}
