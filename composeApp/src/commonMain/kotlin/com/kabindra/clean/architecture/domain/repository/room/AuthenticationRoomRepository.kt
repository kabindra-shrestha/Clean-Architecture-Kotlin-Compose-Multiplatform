package com.kabindra.clean.architecture.domain.repository.room

import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRoomRepository {
    suspend fun getIsLoggedApi(): Flow<Result<Boolean>>

    suspend fun getIsLoggedApiToken(): Flow<Result<String>>

    suspend fun logout(): Flow<Result<Boolean>>
}
