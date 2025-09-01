package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.domain.entity.Profile
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Flow<Result<Profile>>
}