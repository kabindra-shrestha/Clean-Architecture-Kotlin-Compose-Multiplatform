package com.kabindra.clean.architecture.data.repository.room

import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.domain.repository.room.UserRoomRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRoomRepositoryImpl(private val appDatabase: AppDatabase) :
    UserRoomRepository {
    override suspend fun getUser(): Flow<Result<User>> =
        flow {
            emit(Result.Loading)
            try {
                val data = appDatabase.userDao().findAll()
                emit(Result.Success(data[0].toDomain()))
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }
}
