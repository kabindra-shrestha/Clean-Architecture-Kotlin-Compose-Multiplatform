package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.NotificationMarkAsReadRequest
import com.kabindra.clean.architecture.domain.entity.NotificationData
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsAllRead
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsRead
import com.kabindra.clean.architecture.domain.repository.remote.NotificationRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class NotificationUseCase(private val repository: NotificationRepository) {
    suspend fun executeGetNotification(): Flow<Result<NotificationData>> {
        return repository.getNotification()
    }

    suspend fun executeGetNotificationMarkAsAllRead(): Flow<Result<NotificationMarkAsAllRead>> {
        return repository.getNotificationMarkAsAllRead()
    }

    suspend fun executeGetNotificationMarkAsRead(notificationMarkAsReadRequest: NotificationMarkAsReadRequest): Flow<Result<NotificationMarkAsRead>> {
        return repository.getNotificationMarkAsRead(notificationMarkAsReadRequest)
    }
}
