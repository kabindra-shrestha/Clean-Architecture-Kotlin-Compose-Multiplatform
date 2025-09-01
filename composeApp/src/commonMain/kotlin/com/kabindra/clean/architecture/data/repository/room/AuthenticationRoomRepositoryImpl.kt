package com.kabindra.clean.architecture.data.repository.room

import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.di.invalidateAuthTokens
import com.kabindra.clean.architecture.domain.repository.room.AuthenticationRoomRepository
import com.kabindra.clean.architecture.utils.enums.unsubscribeFromTopics
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationRoomRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val client: HttpClient
) :
    AuthenticationRoomRepository {
    override suspend fun getIsLoggedApi(): Flow<Result<Boolean>> =
        flow {
            emit(Result.Loading)
            try {
                val data = appDatabase.apiTokenDao.findAll()
                emit(Result.Success(data.isNotEmpty() && data[0].token.isNotEmpty()))
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun getIsLoggedApiToken(): Flow<Result<String>> =
        flow {
            emit(Result.Loading)
            try {
                val data = appDatabase.apiTokenDao.findAll()
                emit(Result.Success(data[0].token))
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun logout(): Flow<Result<Boolean>> =
        flow {
            emit(Result.Loading)
            try {
                val users = appDatabase.userDao.findAll()
                if (users.isNotEmpty()) {
                    val user = users[0]
                    user.firebase_topics.takeIf { it.isNotEmpty() }?.let { topics ->
                        unsubscribeFromTopics(topics)
                    }
                }
                appDatabase.configDao.deleteAll()
                appDatabase.apiTokenDao.deleteAll()
                appDatabase.userDao.deleteAll()

                invalidateAuthTokens(client)

                emit(Result.Success(true))
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }
}
