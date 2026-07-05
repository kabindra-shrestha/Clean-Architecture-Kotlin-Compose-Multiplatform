package com.kabindra.clean.architecture.domain.usecase.room

import com.kabindra.clean.architecture.domain.repository.room.AuthenticationRoomRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class AuthenticationRoomUseCase(private val repository: AuthenticationRoomRepository) {
    suspend fun executeGetIsLoggedApi(): Flow<Result<Boolean>> {
        return repository.getIsLoggedApi()
    }

    suspend fun executeGetIsLoggedApiToken(): Flow<Result<String>> {
        return repository.getIsLoggedApiToken()
    }

    suspend fun executeLogout(): Flow<Result<Boolean>> {
        return repository.logout()
    }
}
