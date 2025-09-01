package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.ApplyLeaveRequestDTO
import com.kabindra.clean.architecture.data.model.EditLeaveRequestDTO
import com.kabindra.clean.architecture.data.model.LeaveDetailsDTO
import com.kabindra.clean.architecture.data.model.LeaveRequestVerifierDTO
import com.kabindra.clean.architecture.data.model.UpdateLeaveRequestDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.ApplyLeaveRequestDataRequest
import com.kabindra.clean.architecture.data.request.LeaveRequestUpdateDataRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.ApplyLeave
import com.kabindra.clean.architecture.domain.entity.EditLeaveRequest
import com.kabindra.clean.architecture.domain.entity.LeaveDetails
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateLeaveRequest
import com.kabindra.clean.architecture.domain.repository.remote.LeaveRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LeaveRepositoryImpl(
    private val apiDataSource: ApiDataSource,
) : LeaveRepository {

    override suspend fun getApplyLeaveRequest(applyTimeRequestDataRequest: ApplyLeaveRequestDataRequest): Flow<Result<ApplyLeave>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getApplyLeaveRequest(applyTimeRequestDataRequest) ///work in progress
                if (response.status.isSuccess()) {
                    val responses: ApplyLeaveRequestDTO = response.body()

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

    override suspend fun getLeaveRequestVerifiers(): Flow<Result<LeaveRequestVerifier>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getLeaveRequestVerifiers()
                if (response.status.isSuccess()) {
                    val responses: LeaveRequestVerifierDTO = response.body()

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

    override suspend fun getLeaveDetails(): Flow<Result<LeaveDetails>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getLeaveDetails()
                if (response.status.isSuccess()) {
                    val responses: LeaveDetailsDTO = response.body()

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

    override suspend fun getLeaveRequestEdit(ticket_id: String): Flow<Result<EditLeaveRequest>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getLeaveRequestEdit(ticket_id)
                if (response.status.isSuccess()) {
                    val responses: EditLeaveRequestDTO = response.body()

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

    override suspend fun getLeaveRequestUpdate(applyTimeRequestUpdateDataRequest: LeaveRequestUpdateDataRequest): Flow<Result<UpdateLeaveRequest>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getLeaveRequestUpdate(applyTimeRequestUpdateDataRequest)
                if (response.status.isSuccess()) {
                    val responses: UpdateLeaveRequestDTO = response.body()

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


