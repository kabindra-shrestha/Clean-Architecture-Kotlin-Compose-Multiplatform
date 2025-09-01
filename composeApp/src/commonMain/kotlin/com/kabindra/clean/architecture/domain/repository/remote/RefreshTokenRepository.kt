package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.RefreshTokenDataRequest
import com.kabindra.clean.architecture.domain.entity.RefreshToken
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface RefreshTokenRepository {
    suspend fun getRefreshToken(refreshTokenDataRequest: RefreshTokenDataRequest): Flow<Result<RefreshToken>>
}