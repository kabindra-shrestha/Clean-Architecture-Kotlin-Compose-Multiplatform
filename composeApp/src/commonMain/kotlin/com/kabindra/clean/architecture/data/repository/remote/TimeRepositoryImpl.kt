package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.ApplyTimeRequestDTO
import com.kabindra.clean.architecture.data.model.EditTimeRequestDTO
import com.kabindra.clean.architecture.data.model.TimeRequestVerifierDTO
import com.kabindra.clean.architecture.data.model.UpdateTimeRequestDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.ApplyTimeRequestDataRequest
import com.kabindra.clean.architecture.data.request.TimeRequestUpdateDataRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.ApplyTime
import com.kabindra.clean.architecture.domain.entity.EditTimeRequest
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateTimeRequest
import com.kabindra.clean.architecture.domain.repository.remote.TimeRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TimeRepositoryImpl(
    private val apiDataSource: ApiDataSource,
) : TimeRepository {

    override suspend fun getTimeRequestVerifiers(): Flow<Result<TimeRequestVerifier>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getTimeRequestVerifiers()
                if (response.status.isSuccess()) {
                    val responses: TimeRequestVerifierDTO = response.body()

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

    override suspend fun getApplyTimeRequest(applyTimeRequestDataRequest: ApplyTimeRequestDataRequest): Flow<Result<ApplyTime>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getApplyTimeRequest(applyTimeRequestDataRequest)
                if (response.status.isSuccess()) {
                    val responses: ApplyTimeRequestDTO = response.body()

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

    override suspend fun getTimeRequestEdit(ticket_id: String): Flow<Result<EditTimeRequest>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getTimeRequestEdit(ticket_id)
                if (response.status.isSuccess()) {
                    val responses: EditTimeRequestDTO = response.body()

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

    override suspend fun getTimeRequestUpdate(timeRequestUpdateDataRequest: TimeRequestUpdateDataRequest): Flow<Result<UpdateTimeRequest>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getTimeRequestUpdate(timeRequestUpdateDataRequest)
                if (response.status.isSuccess()) {
                    val responses: UpdateTimeRequestDTO = response.body()

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


