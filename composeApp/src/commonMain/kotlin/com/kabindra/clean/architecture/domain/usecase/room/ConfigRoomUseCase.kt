package com.kabindra.clean.architecture.domain.usecase.room

import com.kabindra.clean.architecture.domain.entity.Config
import com.kabindra.clean.architecture.domain.repository.room.ConfigRoomRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class ConfigRoomUseCase(private val repository: ConfigRoomRepository) {
    suspend fun executeGetConfigBaseUrl(): Flow<Result<String>> {
        return repository.getConfigBaseUrl()
    }

    suspend fun executeGetConfigSaveBaseUrl(config: Config): Flow<Result<Boolean>> {
        return repository.getConfigSaveBaseUrl(config)
    }
}