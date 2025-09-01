package com.kabindra.clean.architecture.presentation.ui.screen.drawer

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.compose.rememberNavController
import com.kabindra.clean.architecture.domain.entity.Logout
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.drawer.DrawerComponents
import com.kabindra.clean.architecture.presentation.ui.screen.dashboard.Dashboard
import com.kabindra.clean.architecture.presentation.viewmodel.remote.LogoutViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.room.UserRoomViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.base.EventBus
import com.kabindra.clean.architecture.utils.base.RouteEvent
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.DrawerStateType
import com.kabindra.clean.architecture.utils.enums.MenuType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.handler.AuthenticationHandler
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    logoutViewModel: LogoutViewModel = koinViewModel(),
    userRoomViewModel: UserRoomViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit
) {
    val navController = rememberNavController()

    val scope = rememberCoroutineScope()
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val logoutState by logoutViewModel.logoutState.collectAsState()
    val userState by userRoomViewModel.userState.collectAsState()
    var drawerStateType by remember { mutableStateOf(DrawerStateType.CLOSED) }
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var successType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var userInfo: User? = null
    var userData by remember { mutableStateOf(userInfo) }

    val drawerWidth = 700f
    val translationX = remember { Animatable(0f) }
    translationX.updateBounds(0f, drawerWidth)
    val draggableState = rememberDraggableState(onDelta = { dragAmount ->
        scope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    })
    val decay = rememberSplineBasedDecay<Float>()

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
        user(userRoomViewModel)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DrawerComponents(
            modifier = Modifier, userData,
            onClick = {
                scope.launch {
                    if (drawerStateType == DrawerStateType.OPEN) {
                        translationX.animateTo(0f)
                    } else {
                        translationX.animateTo(drawerWidth)
                    }
                    drawerStateType = if (drawerStateType == DrawerStateType.OPEN) {
                        DrawerStateType.CLOSED
                    } else {
                        DrawerStateType.OPEN
                    }

                    if (it == MenuType.Logout.slug) {
                        logout(logoutViewModel)
                    } else {
                        EventBus.post(RouteEvent(it))
                    }
                }
            })

        Box(
            modifier = Modifier.fillMaxSize()
                .graphicsLayer {
                    this.translationX = translationX.value
                    val scale = lerp(1f, 0.8f, translationX.value / drawerWidth)
                    this.scaleX = scale
                    this.scaleY = scale
                    shape = RoundedCornerShape(
                        size = if (DrawerStateType.OPEN == drawerStateType) 16.dp else 0.dp
                    )
                    clip = true
                }.draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = { velocity: Float ->
                        val decayX = decay.calculateTargetValue(
                            translationX.value,
                            velocity
                        )
                        scope.launch {
                            val targetX = if (decayX > drawerWidth * 0.5f) {
                                drawerWidth
                            } else {
                                0f
                            }

                            val canReachTargetWithDecay =
                                (decayX > targetX && targetX == drawerWidth || (decayX < targetX && targetX == 0f))

                            if (canReachTargetWithDecay) {
                                translationX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay
                                )
                            } else {
                                translationX.animateTo(
                                    targetValue = targetX,
                                    initialVelocity = velocity
                                )
                            }
                            drawerStateType = if (targetX == drawerWidth) {
                                DrawerStateType.OPEN
                            } else {
                                DrawerStateType.CLOSED
                            }
                        }

                    })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Dashboard(
                    drawerStateType == DrawerStateType.OPEN,
                    onNavigateLogin = { onNavigateLogin() },
                    onNavigateSideBar = {
                        scope.launch {
                            if (drawerStateType == DrawerStateType.OPEN) {
                                translationX.animateTo(0f)
                            } else {
                                translationX.animateTo(drawerWidth)
                            }
                            drawerStateType = if (drawerStateType == DrawerStateType.OPEN) {
                                DrawerStateType.CLOSED
                            } else {
                                DrawerStateType.OPEN
                            }
                        }
                    }
                )
            }

            if (showLoading) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    isCircular = true
                )
            }
        }
    }

    LaunchedEffect(logoutState) {
        when (logoutState) {
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

                val logout: Logout =
                    (logoutState as Result.Success<Logout>).data

                successMessage = logout.message
                successType = ResponseType.Logout
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (logoutState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (logoutState as Result.Error).error.message
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
            onDismiss = {
                if (successType == ResponseType.Logout) {
                    AuthenticationHandler().logout(onNavigateLogin = { onNavigateLogin() })
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
}

@Composable
fun DashboardToolbar(
    isDrawerOpen: Boolean,
    onNavigationClick: () -> Unit,
    onNavigateProfile: () -> Unit,
    onNavigateNotification: () -> Unit,
    unreadNotificationCountData: Int?,
    userData: User?
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Crossfade(targetState = isDrawerOpen) { state ->
            val icon = if (state) {
                Icons.AutoMirrored.Filled.MenuOpen
            } else {
                Icons.Default.Menu
            }
            ImageHandlerVector(
                modifier = Modifier.size(32.dp),
                image = icon,
                isClickable = true,
                onClick = { onNavigationClick() }
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ImageHandlerURL(
                    modifier = Modifier.size(32.dp),
                    image = userData?.profile_picture ?: "",
                    contentDescription = "Profile Icon",
                    contentScale = ContentScale.Crop,
                    placeholder = Icons.Default.Person,
                    circular = true,
                    isClickable = true,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                TextComponent(text = userData?.name ?: "", fontWeight = FontWeight.Bold)
                TextComponent(
                    text = "${userData?.code.orEmpty()}, ${userData?.department.orEmpty()}",
                    size = TextSize.Medium
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable { },
        ) {
            ImageHandlerVector(
                modifier = Modifier.size(32.dp),
                image = Icons.Default.Notifications,
                contentDescription = "Notification Icon",
                isClickable = true,
                onClick = { onNavigateNotification() }
            )

            if (unreadNotificationCountData != null && unreadNotificationCountData > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(Color(0xFFEF4444))
                ) {
                    TextComponent(
                        text = "$unreadNotificationCountData",
                        modifier = Modifier.align(Alignment.Center),
                        size = TextSize.Medium
                    )
                }
            }
        }
    }
}

private fun user(
    userRoomViewModel: UserRoomViewModel,
) {
    userRoomViewModel.getUser()
}

private fun logout(
    logoutViewModel: LogoutViewModel
) {
    logoutViewModel.getLogout()
}