package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.NotificationMarkAsReadRequest
import com.kabindra.clean.architecture.domain.entity.NotificationData
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsAllRead
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsRead
import com.kabindra.clean.architecture.domain.usecase.remote.NotificationUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(private val notificationUseCase: NotificationUseCase) : ViewModel() {
    private val _notificationDataState =
        MutableStateFlow<Result<NotificationData>>(Result.Initial)
    val notificationDataState: StateFlow<Result<NotificationData>> get() = _notificationDataState

    private val _notificationMarkAsAllReadState =
        MutableStateFlow<Result<NotificationMarkAsAllRead>>(Result.Initial)
    val notificationMarkAsAllReadState: StateFlow<Result<NotificationMarkAsAllRead>> get() = _notificationMarkAsAllReadState

    private val _notificationMarkAsReadState =
        MutableStateFlow<Result<NotificationMarkAsRead>>(Result.Initial)
    val notificationMarkAsReadState: StateFlow<Result<NotificationMarkAsRead>> get() = _notificationMarkAsReadState


    fun getNotification() {
        viewModelScope.launch {
            notificationUseCase.executeGetNotification().collect { result ->
                _notificationDataState.value = result
            }
        }
    }

    fun getNotificationMarkAsAllRead() {
        viewModelScope.launch {
            notificationUseCase.executeGetNotificationMarkAsAllRead()
                .collect { result ->
                    _notificationMarkAsAllReadState.value = result
                }
        }
    }

    fun getNotificationMarkAsRead(notificationMarkAsReadRequest: NotificationMarkAsReadRequest) {
        viewModelScope.launch {
            notificationUseCase.executeGetNotificationMarkAsRead(notificationMarkAsReadRequest)
                .collect { result ->
                    _notificationMarkAsReadState.value = result
                }
        }
    }

    fun resetStates() {
        _notificationDataState.value = Result.Initial
        _notificationMarkAsAllReadState.value = Result.Initial
        _notificationMarkAsReadState.value = Result.Initial
    }
}