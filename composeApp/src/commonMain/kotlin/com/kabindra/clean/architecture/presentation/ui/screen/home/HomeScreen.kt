package com.kabindra.clean.architecture.presentation.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.Dashboard
import com.kabindra.clean.architecture.domain.entity.DashboardData
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.TextType
import com.kabindra.clean.architecture.presentation.ui.items.ItemHomeMenu
import com.kabindra.clean.architecture.presentation.ui.items.ItemHomeTicket
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.LeaveRequestBottomSheet
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.TimeRequestBottomSheet
import com.kabindra.clean.architecture.presentation.ui.screen.drawer.DashboardToolbar
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.Route
import com.kabindra.clean.architecture.presentation.ui.screen.ticket.TicketDetailBottomSheet
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.viewmodel.remote.DashboardViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.room.UserRoomViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.NavigationManager
import com.kabindra.clean.architecture.utils.base.DashboardRefreshEvent
import com.kabindra.clean.architecture.utils.base.EventBus
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.HomeContentType
import com.kabindra.clean.architecture.utils.enums.MenuType
import com.kabindra.clean.architecture.utils.enums.NotificationTypes
import com.kabindra.clean.architecture.utils.enums.TicketContentType
import com.kabindra.clean.architecture.utils.enums.TicketWorkflowType
import com.kabindra.clean.architecture.utils.enums.getMenuType
import com.kabindra.clean.architecture.utils.enums.getTicketWorkflowType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

private var timeDate: String = ""
private var leaveDate: String = ""
private var ticketId: Int = 0
private var ticketWorkflow: String = ""

@Composable
fun HomeScreen(
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    userRoomViewModel: UserRoomViewModel = koinViewModel(),
    isDrawerOpen: Boolean,
    onNavigateLogin: () -> Unit,
    onNavigateSpecificScreen: (Route) -> Unit,
    onNavigateSideBar: () -> Unit,
    onNavigateProfile: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateTicketScreen: (
        contentArgument: String,
        workflowArgument: String,
    ) -> Unit
) {
    val dashboardState by dashboardViewModel.dashboardState.collectAsState()
    val userState by userRoomViewModel.userState.collectAsState()
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var dashboardDataList: MutableList<DashboardData> = mutableListOf()
    var unreadNotificationCount: Int = 0
    var userInfo: User? = null

    var dashboardData by remember { mutableStateOf(dashboardDataList) }
    var unreadNotificationCountData by remember { mutableStateOf(unreadNotificationCount) }
    var userData by remember { mutableStateOf(userInfo) }
    var dashboardRefreshEvent by remember { mutableStateOf<DashboardRefreshEvent?>(null) }

    var showTimeBottomSheet by remember { mutableStateOf(false) }
    var showLeaveBottomSheet by remember { mutableStateOf(false) }
    var showDetailBottomSheet by remember { mutableStateOf(false) }

    var needUpdate = false

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
        EventBus.nonStickyEvents.collect { event ->
            if (event is DashboardRefreshEvent) {
                dashboardRefreshEvent = event
            }
        }
    }

    if (dashboardRefreshEvent != null) {
        if (dashboardRefreshEvent!!.refresh) {
            LaunchedEffect(Unit) {
                dashboard(dashboardViewModel)
            }

            dashboardRefreshEvent = null
        }
    }

    LaunchedEffect(Unit) {
        NavigationManager.navigationState.collect { event ->
            println("HomeScreen navigationState: ${event.type} ${event.ticket_id} ${event.workflow} ${event.date} ")
            if (event.type!!.isNotEmpty()) {
                if (event.type == NotificationTypes.ApplyTimeRequest.type) {
                    timeDate = event.date!!
                    showTimeBottomSheet = true
                }
                if (event.type == NotificationTypes.ApplyLeaveRequest.type) {
                    leaveDate = event.date!!
                    showLeaveBottomSheet = true
                }
                if (event.type == NotificationTypes.TicketDetail.type) {
                    ticketId = event.ticket_id!!.toInt()
                    ticketWorkflow = event.workflow!!
                    showDetailBottomSheet = true
                }
                delay(5000)
                NavigationManager.resetNavigationState()
            }
        }
    }

    LaunchedEffect(Unit) {
        user(userRoomViewModel)
    }

    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = AppTheme.dimens.bottomNavigationPadding)
        ) {
            DashboardToolbar(
                isDrawerOpen = isDrawerOpen,
                onNavigationClick = { onNavigateSideBar() },
                onNavigateProfile = { onNavigateProfile() },
                onNavigateNotification = { onNavigateNotification() },
                unreadNotificationCountData,
                userData
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BaseLazy(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(0.dp),
                    arrangement = Arrangement.spacedBy(0.dp),
                    items = dashboardData,
                    listType = LazyListType.LIST,
                    scrollDirection = LazyScrollDirection.VERTICAL,
                    itemContent = { index, item ->
                        when (item.type) {
                            HomeContentType.PendingTickets.slug -> {
                                if (item.content!!.isNotEmpty()) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        TextComponent(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(start = 10.dp),
                                            text = item.title!!,
                                            fontWeight = FontWeight.Bold,
                                            type = TextType.Title,
                                            size = TextSize.Large
                                        )

                                        BaseLazy(
                                            modifier = Modifier
                                                .height(
                                                    ((120 * if (item.content.size % 2 == 0) {
                                                        item.content.size / 2
                                                    } else {
                                                        (item.content.size / 2) + 1
                                                    }).toFloat()).roundToInt().dp
                                                ),
                                            contentPadding = PaddingValues(4.dp),
                                            arrangement = Arrangement.spacedBy(4.dp),
                                            items = item.content,
                                            listType = LazyListType.GRID,
                                            scrollDirection = LazyScrollDirection.VERTICAL,
                                            userScrollEnabled = false,
                                            spanCount = 2,
                                            itemContent = { index, item ->
                                                ItemHomeTicket(
                                                    item,
                                                    onClick = {
                                                        onNavigateTicketScreen(
                                                            TicketContentType.ToBeReviewed.slug,
                                                            item.workflow!!
                                                        )
                                                    }
                                                )
                                            },
                                            onLoadMore = { },
                                            onScrollStateChanged = { }
                                        )
                                    }
                                }
                            }

                            HomeContentType.Tickets.slug -> {
                                if (item.content!!.isNotEmpty()) {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        TextComponent(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(start = 10.dp),
                                            text = item.title!!,
                                            fontWeight = FontWeight.Bold,
                                            type = TextType.Title,
                                            size = TextSize.Large
                                        )

                                        BaseLazy(
                                            modifier = Modifier
                                                .height(
                                                    ((120 * if (item.content.size % 2 == 0) {
                                                        item.content.size / 2
                                                    } else {
                                                        (item.content.size / 2) + 1
                                                    }).toFloat()).roundToInt().dp
                                                ),
                                            contentPadding = PaddingValues(4.dp),
                                            arrangement = Arrangement.spacedBy(4.dp),
                                            items = item.content,
                                            listType = LazyListType.GRID,
                                            scrollDirection = LazyScrollDirection.VERTICAL,
                                            userScrollEnabled = false,
                                            spanCount = 2,
                                            itemContent = { index, item ->
                                                ItemHomeTicket(
                                                    item,
                                                    onClick = {
                                                        onNavigateTicketScreen(
                                                            TicketContentType.MyRequests.slug,
                                                            item.workflow!!
                                                        )
                                                    }
                                                )
                                            },
                                            onLoadMore = { },
                                            onScrollStateChanged = { }
                                        )
                                    }
                                }
                            }

                            HomeContentType.Menu.slug -> {
                                if (item.content!!.isNotEmpty()) {
                                    BaseLazy(
                                        modifier = Modifier.fillMaxWidth()
                                            .height(((65 * (item.content.size)).toFloat()).roundToInt().dp),
                                        contentPadding = PaddingValues(4.dp),
                                        arrangement = Arrangement.spacedBy(4.dp),
                                        items = item.content,
                                        listType = LazyListType.LIST,
                                        scrollDirection = LazyScrollDirection.VERTICAL,
                                        userScrollEnabled = false,
                                        itemContent = { index, item ->
                                            ItemHomeMenu(
                                                item,
                                                onClick = { selectedRoute ->
                                                    val navigationRoute: Route =
                                                        getMenuType<MenuType>(selectedRoute).route!!

                                                    onNavigateSpecificScreen(navigationRoute)
                                                }
                                            )
                                        },
                                        onLoadMore = { },
                                        onScrollStateChanged = { }
                                    )
                                }
                            }
                        }
                    },
                    onLoadMore = { },
                    onScrollStateChanged = { },
                )
            }
        }

        if (showLoading) {
            LoadingIndicator(
                modifier = Modifier.align(Alignment.Center),
                isCircular = true
            )
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
                        showTimeBottomSheet = false

                        dashboard(dashboardViewModel)
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
                        showLeaveBottomSheet = false

                        dashboard(dashboardViewModel)
                    },
                    onDismissRequest = {
                        showLeaveBottomSheet = false
                    }
                )
            }
        )
    }
    if (showDetailBottomSheet) {
        ModalBottomSheetComponent(
            modifier = Modifier.padding(
                AppTheme.dimens.paddingSmall
            ),
            title = "${getTicketWorkflowType<TicketWorkflowType>(ticketWorkflow).title} (Ticket No: ${ticketId})",
            isVisible = showDetailBottomSheet,
            isExpanded = true,
            icon = Icons.Default.Close,  // Pass the icon you want to use for closing
            onDismiss = {
                showDetailBottomSheet = false

                ticketId = 0
                ticketWorkflow = ""

                if (needUpdate) {
                    dashboard(dashboardViewModel)

                    needUpdate = false
                }
            },
            content = {
                TicketDetailBottomSheet(
                    ticketId = ticketId,
                    onActionDone = {
                        // showTicketDetailBottomSheet = false

                        ticketId = 0
                        ticketWorkflow = ""

                        needUpdate = it
                    },
                    onClose = {
                        showDetailBottomSheet = false

                        ticketId = 0
                        ticketWorkflow = ""

                        if (needUpdate) {
                            dashboard(dashboardViewModel)

                            needUpdate = false
                        }
                    }
                )
            }
        )
    }

    LaunchedEffect(dashboardState) {
        when (dashboardState) {
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

                val dashboard: Dashboard =
                    (dashboardState as Result.Success<Dashboard>).data

                dashboardDataList = dashboard.response?.data as MutableList<DashboardData>
                unreadNotificationCount = dashboard.response.unread_notification_count!!

                dashboardData = dashboardDataList
                unreadNotificationCountData = unreadNotificationCount
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (dashboardState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (dashboardState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(userState) {
        when (userState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = false
                showSuccess = false
                showError = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = false
                showError = false

                val user: User =
                    (userState as Result.Success<User>).data

                userInfo = user

                userData = userInfo

                dashboard(dashboardViewModel)
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (userState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (userState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    if (showSuccess) {
        GlobalSuccessDialog(
            isVisible = showSuccess,
            isAction = true,
            message = successMessage,
            onDismiss = { })
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
}

private fun dashboard(
    dashboardViewModel: DashboardViewModel,
) {
    dashboardViewModel.getDashboard()
}

private fun user(
    userRoomViewModel: UserRoomViewModel,
) {
    userRoomViewModel.getUser()
}