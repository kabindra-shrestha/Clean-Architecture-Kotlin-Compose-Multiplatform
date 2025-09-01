package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.domain.entity.Logout
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface LogoutRepository {
    suspend fun getLogout(): Flow<Result<Logout>>
}
