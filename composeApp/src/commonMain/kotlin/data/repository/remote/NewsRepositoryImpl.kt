package data.repository.remote

import data.model.toDomain
import data.source.remote.NewsDataSource
import domain.entity.News
import domain.repository.remote.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.NetworkResult

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
