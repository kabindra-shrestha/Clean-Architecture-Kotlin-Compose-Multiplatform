package com.kabindra.clean.architecture.presentation.ui.screen.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kabindra.clean.architecture.data.request.NotificationMarkAsReadRequest
import com.kabindra.clean.architecture.domain.entity.Notification
import com.kabindra.clean.architecture.domain.entity.NotificationData
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsAllRead
import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsRead
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.component.ShowEmpty
import com.kabindra.clean.architecture.presentation.ui.component.TopAppBarWithBackComponent
import com.kabindra.clean.architecture.presentation.ui.items.ItemNotification
import com.kabindra.clean.architecture.presentation.ui.items.NotificationIconTextComponent
import com.kabindra.clean.architecture.presentation.ui.items.NotificationType
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.LeaveRequestBottomSheet
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.TimeRequestBottomSheet
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.Route
import com.kabindra.clean.architecture.presentation.ui.screen.ticket.TicketDetailBottomSheet
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.viewmodel.remote.NotificationViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.confirmation.GlobalConfirmationDialog
import com.kabindra.clean.architecture.utils.constants.ConfirmationType
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.NotificationTypes
import com.kabindra.clean.architecture.utils.enums.TicketWorkflowType
import com.kabindra.clean.architecture.utils.enums.getTicketWorkflowType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import org.koin.compose.viewmodel.koinViewModel

private var timeDate: String = ""
private var leaveDate: String = ""
private var ticketId: Int = 0
private var ticketWorkflow: String = ""
private var notificationId: String = ""


@Composable
fun NotificationScreen(
    notificationViewModel: NotificationViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit,
    onBackNavigate: () -> Unit
) {
    rememberLazyListState()
    val navController = rememberNavController()

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val notificationState by notificationViewModel.notificationDataState.collectAsState()
    val notificationMarkAsAllReadState by notificationViewModel.notificationMarkAsAllReadState.collectAsState()
    val notificationMarkAsReadState by notificationViewModel.notificationMarkAsReadState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var showEmpty by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var successType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var showConfirmation by remember { mutableStateOf(false) }
    var confirmationMessage by remember { mutableStateOf("") }
    var confirmationType by remember { mutableStateOf<ConfirmationType>(ConfirmationType.None) }

    var notificationList: MutableList<Notification> = mutableListOf()
    var notification by remember { mutableStateOf(notificationList) }
    var unreadNotificationList: MutableList<Notification> = mutableListOf()
    var unreadNotification by remember { mutableStateOf(unreadNotificationList) }

    var showTimeBottomSheet by remember { mutableStateOf(false) }
    var showLeaveBottomSheet by remember { mutableStateOf(false) }
    var showDetailBottomSheet by remember { mutableStateOf(false) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = backStackEntry?.destination?.route?.let { mutableStateOf(it) }
        ?: mutableStateOf(Route.HomeMainRoute::class.qualifiedName)

    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = errorStatusCode,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            /*onDismiss = {
                showError = false
                errorStatusCode = -1
                errorTitle = ""
                errorMessage = ""
            },*/
        )
        return
    }

    LaunchedEffect(Unit) {
        notification(notificationViewModel)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = AppTheme.dimens.bottomNavigationPadding),
    ) {
        TopAppBarWithBackComponent(
            title = "Notification",
            onBackNavigate = { onBackNavigate() })
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (unreadNotification.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        NotificationIconTextComponent(
                            icon = Icons.Default.Check, text = "Mark as all read",
                            isClickable = true, onClick = {
                                showConfirmation = true
                                confirmationMessage = "Are you sure to mark as all read?"
                                confirmationType = ConfirmationType.NotificationMarkAllAsRead
                            }
                        )
                    }
                }

                if (notification.isNotEmpty()) {
                    BaseLazy(
                        modifier = Modifier
                            .wrapContentHeight(),
                        contentPadding = PaddingValues(0.dp),
                        arrangement = Arrangement.spacedBy(0.dp),
                        items = notification,
                        listType = LazyListType.LIST,
                        scrollDirection = LazyScrollDirection.VERTICAL,
                        itemContent = { index, item ->
                            if (item.id == NotificationType.NotificationHeader.slug) {
                                NotificationIconTextComponent(
                                    icon = Icons.Default.PushPin,
                                    text = item.title!!
                                )
                            } else {
                                ItemNotification(
                                    item,
                                    onNotificationItemClick = {
                                        if (item.meta?.type == NotificationTypes.ApplyTimeRequest.type) {
                                            timeDate = item.meta.date!!
                                            notificationId = item.id!!
                                            showTimeBottomSheet = true
                                        }
                                        if (item.meta?.type == NotificationTypes.ApplyLeaveRequest.type) {
                                            leaveDate = item.meta.date!!
                                            notificationId = item.id!!
                                            showLeaveBottomSheet = true
                                        }
                                        if (item.meta?.type == NotificationTypes.TicketDetail.type) {
                                            ticketId = item.meta.ticket_id!!
                                            ticketWorkflow = item.meta.workflow!!
                                            showDetailBottomSheet = true
                                        }
                                    },
                                    onClick = {
                                        notificationMarkAsRead(
                                            notificationViewModel,
                                            item.id.toString()
                                        )
                                    }
                                )
                            }
                        },
                        onLoadMore = {},
                        onScrollStateChanged = { },
                    )
                } else {
                    if (showEmpty) {
                        ShowEmpty()
                    }
                }
            }

            if (showLoading) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    isCircular = true
                )
            }
        }
    }

    if (showTimeBottomSheet) {
        ModalBottomSheetComponent(
            modifier = Modifier.padding(
                AppTheme.dimens.paddingSmall
            ),
            title = "Apply Time Request",
            isVisible = showTimeBottomSheet,
            isExpanded = true,
            icon = Icons.Default.Close,
            onDismiss = {
                showTimeBottomSheet = false
            },
            content = {
                TimeRequestBottomSheet(
                    attendanceDate = timeDate,
                    attendanceLogin = "",
                    attendanceLogout = "",
                    onNavigateLogin = { onNavigateLogin() },
                    onTimeRequestApplied = {
                        notificationMarkAsRead(
                            notificationViewModel,
                            notificationId
                        )
                        showTimeBottomSheet = false
                    },
                    onDismissRequest = {
                        showTimeBottomSheet = false
                    }
                )
            }
        )
    }
    if (showLeaveBottomSheet) {
        ModalBottomSheetComponent(
            modifier = Modifier.padding(
                AppTheme.dimens.paddingSmall
            ),
            title = "Apply Leave Request",
            isVisible = showLeaveBottomSheet,
            isExpanded = true,
            icon = Icons.Default.Close,
            onDismiss = {
                showLeaveBottomSheet = false
            },
            content = {
                LeaveRequestBottomSheet(
                    attendanceDate = leaveDate,
                    onNavigateLogin = { onNavigateLogin() },
                    onLeaveRequestApplied = {
                        notificationMarkAsRead(
                            notificationViewModel,
                            notificationId
                        )

                        showLeaveBottomSheet = false
                    },
                    onDismissRequest = {
                        showLeaveBottomSheet = false
                    }
                )
            }
        )
    }
    if (showDetailBottomSheet) {
        /*showLoading = false
        showSuccess = false
        showError = false*/

        ModalBottomSheetComponent(
            modifier = Modifier.padding(
                AppTheme.dimens.paddingSmall
            ),
            title = "${getTicketWorkflowType<TicketWorkflowType>(ticketWorkflow).title} (Ticket No: $ticketId)",
            isVisible = showDetailBottomSheet,
            isExpanded = true,
            icon = Icons.Default.Close,  // Pass the icon you want to use for closing
            onDismiss = {
                showDetailBottomSheet = false

                ticketId = 0
                ticketWorkflow = ""

                /*if (needUpdate) {
                    if (currentPageIndex == pageIndex) {
                        ticketFilter(ticketViewModel, content)
                    }
                    needUpdate = false
                }*/
            },
            content = {
                TicketDetailBottomSheet(
                    ticketId = ticketId,
                    onActionDone = {
                        // showTicketDetailBottomSheet = false

                        ticketId = 0
                        ticketWorkflow = ""

                        /* actionTitleClicked = ""
                         actionSlugClicked = ""
                         actionOwnerClicked = listOf()
                         actionRemarksClicked = ""

                         needUpdate = it*/
                    },
                    onClose = {
                        showDetailBottomSheet = false

                        ticketId = 0
                        ticketWorkflow = ""

                        /*  if (needUpdate) {
                              if (currentPageIndex == pageIndex) {
                                  ticketFilter(ticketViewModel, content)
                              }
                              needUpdate = false
                          }*/
                    }
                )
            }
        )
    }


    LaunchedEffect(notificationState) {
        when (notificationState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = true
                showSuccess = false
                showError = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = false
                showError = false

                val notificationData = (notificationState as Result.Success<NotificationData>).data

                if (notificationData.response?.unread_notifications?.isNotEmpty()!!) {
                    var unreadNotifications = mutableListOf<Notification>()
                    for (notifications in notificationData.response.unread_notifications) {
                        notifications.isUnread = true
                        unreadNotifications.add(notifications)
                    }
                    notificationList.add(
                        Notification(
                            id = NotificationType.NotificationHeader.slug,
                            title = "Unread Notification"
                        )
                    )
                    notificationList.addAll(unreadNotifications)
                    unreadNotificationList = unreadNotifications
                    unreadNotification = unreadNotificationList
                }

                if (notificationData.response.previous_notifications?.isNotEmpty()!!) {
                    notificationList.add(
                        Notification(
                            id = NotificationType.NotificationHeader.slug,
                            title = "Previous Notification"
                        )
                    )
                    notificationList.addAll(notificationData.response.previous_notifications as MutableList<Notification>)
                }
                notification = notificationList

            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (notificationState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (notificationState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }
    LaunchedEffect(notificationMarkAsAllReadState) {
        when (notificationMarkAsAllReadState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = true
                showSuccess = false
                showError = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = true
                showError = false

                val notificationMarkAllAsRead =
                    (notificationMarkAsAllReadState as Result.Success<NotificationMarkAsAllRead>).data
                successMessage = notificationMarkAllAsRead.message
                successType = ResponseType.NotificationMarkAllAsRead
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (notificationMarkAsAllReadState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (notificationMarkAsAllReadState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(notificationMarkAsReadState) {
        when (notificationMarkAsReadState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = true
                showSuccess = false
                showError = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = true
                showError = false

                val notificationMarkAsRead =
                    (notificationMarkAsReadState as Result.Success<NotificationMarkAsRead>).data
                successMessage = notificationMarkAsRead.message
                successType = ResponseType.NotificationMarkAsRead
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (notificationMarkAsReadState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (notificationMarkAsReadState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    if (showSuccess) {
        GlobalSuccessDialog(
            isVisible = showSuccess,
            isAction = true,
            message = successMessage,
            onDismiss = {
                if (successType == ResponseType.NotificationMarkAllAsRead || successType == ResponseType.NotificationMarkAsRead) {
                    notificationList.clear()
                    unreadNotification.clear()
                    notification(notificationViewModel)
                }
            })
    }

    if (showError) {
        GlobalErrorDialog(
            isVisible = showError,
            isAction = true,
            statusCode = errorStatusCode,
            title = errorTitle,
            message = errorMessage,
            onDismiss = {
                showError = false
                errorStatusCode = -1
                errorTitle = ""
                errorMessage = ""
            },
            onNavigateLogin = { onNavigateLogin() })
    }

    if (showConfirmation) {
        GlobalConfirmationDialog(
            isVisible = showConfirmation,
            isAction = true,
            message = confirmationMessage,
            onCancel = {
                showConfirmation = false
            },
            onContinue = {
                showConfirmation = false
                if (confirmationType == ConfirmationType.NotificationMarkAllAsRead) {
                    notificationMarkAsAllRead(notificationViewModel)
                }

            }
        )
    }
}

private fun notification(
    notificationViewModel: NotificationViewModel,
) {
    notificationViewModel.getNotification()
}

private fun notificationMarkAsAllRead(notificationViewModel: NotificationViewModel) {
    notificationViewModel.getNotificationMarkAsAllRead()
}

private fun notificationMarkAsRead(notificationViewModel: NotificationViewModel, id: String) {
    notificationViewModel.getNotificationMarkAsRead(NotificationMarkAsReadRequest(id))
}

