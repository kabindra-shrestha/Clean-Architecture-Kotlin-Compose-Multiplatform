package com.kabindra.clean.architecture.presentation.ui.screen.dashboard

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kabindra.clean.architecture.presentation.ui.component.BottomNavigationBarComponent
import com.kabindra.clean.architecture.presentation.ui.component.ExpandableFabComponent
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.AttendanceScreen
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.LeaveRequestBottomSheet
import com.kabindra.clean.architecture.presentation.ui.screen.attendance.TimeRequestBottomSheet
import com.kabindra.clean.architecture.presentation.ui.screen.home.HomeScreen
import com.kabindra.clean.architecture.presentation.ui.screen.menu.MenuScreen
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.AppBackHandler
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.Route
import com.kabindra.clean.architecture.presentation.ui.screen.notification.NotificationScreen
import com.kabindra.clean.architecture.presentation.ui.screen.profile.ProfileScreen
import com.kabindra.clean.architecture.presentation.ui.screen.ticket.TicketScreen
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.base.DashboardRefreshEvent
import com.kabindra.clean.architecture.utils.base.EventBus
import com.kabindra.clean.architecture.utils.base.RouteEvent
import com.kabindra.clean.architecture.utils.enums.DashboardExpandableFabType
import com.kabindra.clean.architecture.utils.enums.MenuType
import com.kabindra.clean.architecture.utils.enums.getMenuType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog

@Composable
fun Dashboard(
    isDrawerOpen: Boolean,
    onNavigateLogin: () -> Unit,
    onNavigateSideBar: () -> Unit
) {
    val navController = rememberNavController()

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    var routeEvent by remember { mutableStateOf<RouteEvent?>(null) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = backStackEntry?.destination?.route?.let { mutableStateOf(it) }
        ?: mutableStateOf(Route.HomeMainRoute::class.qualifiedName)

    var showTimeBottomSheet by remember { mutableStateOf(false) }
    var showLeaveBottomSheet by remember { mutableStateOf(false) }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states

            showLoading = false
            showSuccess = false
            showError = false

            expanded = false
        }
    }

    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = errorStatusCode,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            onDismiss = {
                showError = false
                errorStatusCode = -1
                errorTitle = ""
                errorMessage = ""
            },
        )
        return
    }

    LaunchedEffect(Unit) {
        EventBus.nonStickyEvents.collect { event ->
            if (event is RouteEvent) {
                routeEvent = event
            }
        }
    }

    if (routeEvent != null) {
        LaunchedEffect(routeEvent) {
            val navigationRoute: Route =
                getMenuType<MenuType>(routeEvent!!.route).route!!

            navController.navigate(navigationRoute) {
                popUpTo(navController.graph.findStartDestination().route!!) {
                    saveState = true
                }

                launchSingleTop = true

                restoreState = true
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBarComponent(
                selectedRoute = currentRoute.value!!,
                onClick = { selectedSlug ->
                    if (getMenuType<MenuType>(selectedSlug).route != null) {
                        val navigationRoute: Route =
                            getMenuType<MenuType>(selectedSlug).route!!

                        navController.navigate(navigationRoute) {
                            popUpTo(navController.graph.findStartDestination().route!!) {
                                saveState = true
                            }

                            launchSingleTop = true

                            restoreState = true
                        }
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExpandableFabComponent(
                modifier = Modifier
                    .offset(y = 47.dp),
                shape = CircleShape,
                expanded = expanded,
                items = DashboardExpandableFabType.entries,
                itemTitle = { it.title!! },
                itemIcon = { it.icon!! },
                onItemClick = { selectedItem ->
                    when (selectedItem.slug) {
                        DashboardExpandableFabType.TimeRequestApproval.slug -> {
                            showTimeBottomSheet = true
                        }

                        DashboardExpandableFabType.LeaveApproval.slug -> {
                            showLeaveBottomSheet = true
                        }
                    }

                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeMainRoute
        ) {
            navigation<Route.HomeMainRoute>(startDestination = Route.HomeRoute) {
                composable<Route.HomeRoute> {
                    HomeScreen(
                        isDrawerOpen = isDrawerOpen,
                        onNavigateLogin = {
                            onNavigateLogin()
                        },
                        onNavigateSpecificScreen = {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        },
                        onNavigateSideBar = {
                            onNavigateSideBar()
                        },
                        onNavigateProfile = {
                            navController.navigate(
                                Route.ProfileRoute
                            ) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        },
                        onNavigateNotification = {
                            navController.navigate(
                                Route.NotificationRoute
                            ) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        },
                        onNavigateTicketScreen = { content, workflow ->
                            navController.navigate(
                                Route.TicketRoute(
                                    content = content,
                                    workflow = workflow
                                )
                            ) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        }
                    )
                }
                composable<Route.ProfileRoute> {
                    ProfileScreen(
                        onNavigateLogin = { onNavigateLogin() },
                        onBackNavigate = { AppBackHandler(navController) },
                    )
                }
                composable<Route.NotificationRoute> {
                    NotificationScreen(
                        onNavigateLogin = { onNavigateLogin() },
                        onBackNavigate = { AppBackHandler(navController) }
                    )
                }
                composable<Route.AttendanceRoute> {
                    AttendanceScreen(
                        onNavigateLogin = { onNavigateLogin() },
                        onBackNavigate = { AppBackHandler(navController) }
                    )
                }
                composable<Route.TicketRoute> { entry ->
                    val arguments = entry.toRoute<Route.TicketRoute>()
                    TicketScreen(
                        onNavigateLogin = { onNavigateLogin() },
                        onBackNavigate = { AppBackHandler(navController) },
                        contentArgument = arguments.content!!,
                        workflowArgument = arguments.workflow!!
                    )
                }
                composable<Route.MenuRoute> {
                    MenuScreen()
                }
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
                    attendanceDate = "",
                    attendanceLogin = "",
                    attendanceLogout = "",
                    onNavigateLogin = { onNavigateLogin() },
                    onTimeRequestApplied = {
                        if (currentRoute.value == MenuType.Home.route!!::class.qualifiedName) {
                            EventBus.post(DashboardRefreshEvent(true))
                        } else {
                            navController.navigate(Route.HomeMainRoute) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        }

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
                    attendanceDate = "",
                    onNavigateLogin = { onNavigateLogin() },
                    onLeaveRequestApplied = {
                        if (currentRoute.value == MenuType.Home.route!!::class.qualifiedName) {
                            EventBus.post(DashboardRefreshEvent(true))
                        } else {
                            navController.navigate(Route.HomeMainRoute) {
                                popUpTo(navController.graph.findStartDestination().route!!) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        }

                        showLeaveBottomSheet = false
                    },
                    onDismissRequest = {
                        showLeaveBottomSheet = false
                    }
                )
            }
        )
    }
}

