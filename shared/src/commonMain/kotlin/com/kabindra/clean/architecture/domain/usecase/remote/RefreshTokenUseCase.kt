package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.RefreshTokenDataRequest
import com.kabindra.clean.architecture.domain.entity.RefreshToken
import com.kabindra.clean.architecture.domain.repository.remote.RefreshTokenRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class RefreshTokenUseCase(private val repository: RefreshTokenRepository) {
    suspend fun executeGetRefreshToken(refreshTokenDataRequest: RefreshTokenDataRequest): Flow<Result<RefreshToken>> {
        return repository.getRefreshToken(refreshTokenDataRequest)
    }
}