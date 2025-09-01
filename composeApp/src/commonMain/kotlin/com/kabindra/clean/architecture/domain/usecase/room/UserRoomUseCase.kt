package com.kabindra.clean.architecture.domain.usecase.room

import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.domain.repository.room.UserRoomRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class UserRoomUseCase(private val repository: UserRoomRepository) {
    suspend fun executeGetUser(): Flow<Result<User>> {
        return repository.getUser()
    }
}
