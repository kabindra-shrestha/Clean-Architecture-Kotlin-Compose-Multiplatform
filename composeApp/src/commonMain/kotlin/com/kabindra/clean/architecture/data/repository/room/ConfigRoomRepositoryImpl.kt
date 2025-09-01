package com.kabindra.clean.architecture.data.repository.room

import com.kabindra.clean.architecture.data.model.ConfigDTO
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.di.BaseUrlProvider
import com.kabindra.clean.architecture.domain.entity.Config
import com.kabindra.clean.architecture.domain.repository.room.ConfigRoomRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConfigRoomRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val baseUrlProvider: BaseUrlProvider
) : ConfigRoomRepository {

    override suspend fun getConfigBaseUrl(): Flow<Result<String>> =
        flow {
            emit(Result.Loading)
            try {
                val data = appDatabase.configDao.findAll()
                emit(Result.Success(data[0].base_url))
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun getConfigSaveBaseUrl(config: Config): Flow<Result<Boolean>> =
        flow {
            emit(Result.Loading)
            try {
                appDatabase.configDao.deleteAll()

                appDatabase.configDao.add(ConfigDTO(config.base_url!!))

                baseUrlProvider.updateBaseUrl(config.base_url)

                emit(Result.Success(true))
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

}