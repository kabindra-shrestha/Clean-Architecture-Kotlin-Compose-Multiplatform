package com.kabindra.clean.architecture.domain.repository.room

import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface UserRoomRepository {
    suspend fun getUser(): Flow<Result<User>>
}
