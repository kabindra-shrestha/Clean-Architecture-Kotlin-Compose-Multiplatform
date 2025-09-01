package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.domain.entity.Profile
import com.kabindra.clean.architecture.domain.repository.remote.ProfileRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class ProfileUseCase(private val repository: ProfileRepository) {
    suspend fun executeGetProfile(): Flow<Result<Profile>> {
        return repository.getProfile()
    }

}
