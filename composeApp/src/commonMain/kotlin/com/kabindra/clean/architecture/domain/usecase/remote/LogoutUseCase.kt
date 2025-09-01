package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.domain.entity.Logout
import com.kabindra.clean.architecture.domain.repository.remote.LogoutRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val repository: LogoutRepository) {
    suspend fun executeGetLogout(): Flow<Result<Logout>> {
        return repository.getLogout()
    }
}
