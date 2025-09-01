package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.MPINSetDTO
import com.kabindra.clean.architecture.data.model.MPINVerifyDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.MPINSetDataRequest
import com.kabindra.clean.architecture.data.request.MPINVerifyDataRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.MPINSet
import com.kabindra.clean.architecture.domain.entity.MPINVerify
import com.kabindra.clean.architecture.domain.repository.remote.MPINRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MPINRepositoryImpl(
    private val apiDataSource: ApiDataSource
) : MPINRepository {

    override suspend fun getMPINSet(mPINSetDataRequest: MPINSetDataRequest): Flow<Result<MPINSet>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getMPINSet(mPINSetDataRequest)
                if (response.status.isSuccess()) {
                    val responses: MPINSetDTO = response.body()

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

    override suspend fun getMPINVerify(mPINVerifyDataRequest: MPINVerifyDataRequest): Flow<Result<MPINVerify>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getMPINVerify(mPINVerifyDataRequest)
                if (response.status.isSuccess()) {
                    val responses: MPINVerifyDTO = response.body()

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


