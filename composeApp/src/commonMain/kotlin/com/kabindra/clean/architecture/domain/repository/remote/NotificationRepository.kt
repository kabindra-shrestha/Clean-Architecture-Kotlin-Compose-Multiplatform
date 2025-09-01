package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.NotificationMarkAsReadRequest
import com.kabindra.clean.architecture.domain.entity.NotificationData
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsAllRead
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsRead
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotification(): Flow<Result<NotificationData>>
    suspend fun getNotificationMarkAsAllRead(): Flow<Result<NotificationMarkAsAllRead>>
    suspend fun getNotificationMarkAsRead(notificationMarkAsReadRequest: NotificationMarkAsReadRequest): Flow<Result<NotificationMarkAsRead>>
}
