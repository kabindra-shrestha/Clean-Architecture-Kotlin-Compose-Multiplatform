package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.NotificationDataDTO
import com.kabindra.clean.architecture.data.model.NotificationMarkAsAllReadDTO
import com.kabindra.clean.architecture.data.model.NotificationMarkAsReadDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.NotificationMarkAsReadRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.NotificationData
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsAllRead
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsRead
import com.kabindra.clean.architecture.domain.repository.remote.NotificationRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl(
    private val apiDataSource: ApiDataSource
) : NotificationRepository {

    override suspend fun getNotification(): Flow<Result<NotificationData>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getNotification()
                if (response.status.isSuccess()) {
                    val responses: NotificationDataDTO = response.body()

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

    override suspend fun getNotificationMarkAsAllRead(): Flow<Result<NotificationMarkAsAllRead>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getNotificationMarkAsAllRead()
                if (response.status.isSuccess()) {
                    val responses: NotificationMarkAsAllReadDTO = response.body()

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

    override suspend fun getNotificationMarkAsRead(notificationMarkAsReadRequest: NotificationMarkAsReadRequest): Flow<Result<NotificationMarkAsRead>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getNotificationMarkAsRead(notificationMarkAsReadRequest)
                if (response.status.isSuccess()) {
                    val responses: NotificationMarkAsReadDTO = response.body()

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


