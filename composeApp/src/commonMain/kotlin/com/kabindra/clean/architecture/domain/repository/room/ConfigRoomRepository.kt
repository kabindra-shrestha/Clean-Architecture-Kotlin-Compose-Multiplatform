package com.kabindra.clean.architecture.domain.repository.room

import com.kabindra.clean.architecture.domain.entity.Config
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface ConfigRoomRepository {
    suspend fun getConfigBaseUrl(): Flow<Result<String>>
    suspend fun getConfigSaveBaseUrl(config: Config): Flow<Result<Boolean>>
}