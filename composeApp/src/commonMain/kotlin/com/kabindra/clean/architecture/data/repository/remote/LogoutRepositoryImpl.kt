package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.LogoutDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.Logout
import com.kabindra.clean.architecture.domain.repository.remote.LogoutRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogoutRepositoryImpl(
    private val apiDataSource: ApiDataSource
) : LogoutRepository {

    override suspend fun getLogout(): Flow<Result<Logout>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getLogout()
                if (response.status.isSuccess()) {
                    val responses: LogoutDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        emit(Result.Success(responses.toDomain()))
                    } else {
                        emit(Result.Error(ResultError.parseError(response)))
                    }
                } else {
                    emit(Result.Error(ResultError.parseError(response)))
                }
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

}