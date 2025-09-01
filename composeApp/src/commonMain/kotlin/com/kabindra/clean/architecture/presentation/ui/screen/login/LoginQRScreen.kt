package com.kabindra.clean.architecture.presentation.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.domain.entity.Config
import com.kabindra.clean.architecture.domain.entity.LoginCheckUser
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextType
import com.kabindra.clean.architecture.presentation.ui.component.animation.doublePulseEffect
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.ui.theme.iconColorPrimary
import com.kabindra.clean.architecture.presentation.ui.theme.onBackgroundLight
import com.kabindra.clean.architecture.presentation.viewmodel.remote.LoginViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.room.ConfigRoomViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.base.ErrorEvent
import com.kabindra.clean.architecture.utils.base.EventBus
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.subscribeToTopics
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.getToken
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

private var firebaseToken = ""

@Composable
fun LoginQRScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    configRoomViewModel: ConfigRoomViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit,
    onNavigateLoginQrScanner: () -> Unit,
    onNavigateLoginVerifyOTP: (
        usernameArgument: String,
        appLoginCodeArgument: String,
    ) -> Unit,
    onNavigateMPINSet: () -> Unit,
    onNavigateMPINVerify: () -> Unit,
    onNavigateDashboard: () -> Unit
) {
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val loginCheckUserState by loginViewModel.loginCheckUserState.collectAsState()
    val configRoomState by configRoomViewModel.configBaseUrlSaveState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var configEvent by remember { mutableStateOf<Config?>(null) }
    var errorEvent by remember { mutableStateOf<ErrorEvent?>(null) }
    var userInfo: LoginCheckUser? = null

    var loginRefreshUserData by remember { mutableStateOf(userInfo) }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
            loginViewModel.resetStates()
            configRoomViewModel.resetStates()

            showLoading = false
            showSuccess = false
            showError = false
            configEvent = null
            errorEvent = null
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
        EventBus.observeSticky(Config::class).collect { event ->
            configEvent = event

            EventBus.clearStickyEvent(Config::class)
        }
    }

    LaunchedEffect(Unit) {
        EventBus.observeSticky(ErrorEvent::class).collect { event ->
            errorEvent = event

            EventBus.clearStickyEvent(ErrorEvent::class)
        }
    }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val token = getToken()
            if (token != null) {
                println("Firebase Token: LoginQRScreen $token")
                firebaseToken = token
            } else {
                println("Firebase Token fetch failed")
            }
        } catch (e: Exception) {
            println("Firebase Error fetching token: ${e.message}")
        }
    }

    if (configEvent != null) {
        LaunchedEffect(Unit) {
            configRoomViewModel.getConfigSaveBaseUrl(configEvent!!)
        }
    }

    if (errorEvent != null) {
        showLoading = false
        showSuccess = false
        showError = errorEvent!!.isVisible
        errorStatusCode = errorEvent!!.statusCode
        errorTitle = errorEvent!!.title
        errorMessage = errorEvent!!.message
        errorType = ResponseType.None
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextComponent(
            modifier = Modifier
                .padding(top = AppTheme.dimens.paddingSmall)
                .align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                append("Welcome To ")
                withStyle(SpanStyle(color = Color(0xFF05B298))) {
                    append("Compose Multiplatform")
                }
            },
            type = TextType.Headline,
            textAlign = TextAlign.Center,
        )


        Spacer(modifier = Modifier.height(70.dp))
        Box(
            modifier = Modifier
                .size(160.dp)
                .doublePulseEffect(targetScale = 2f)
                .clip(CircleShape)
                .background(
                    color = iconColorPrimary,
                    shape = CircleShape
                )
                .clickable { onNavigateLoginQrScanner() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ImageHandlerVector(
                    modifier = Modifier.size(70.dp),
                    image = Icons.Default.QrCodeScanner,
                    contentDescription = "Scan QR",
                    tint = onBackgroundLight
                )
                TextComponent(
                    modifier = Modifier.padding(top = AppTheme.dimens.paddingTooSmall),
                    text = "Tap To Scan",
                    color = onBackgroundLight,
                )
            }
        }
    }

    LaunchedEffect(configRoomState) {
        when (configRoomState) {
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

                (configRoomState as Result.Success<Boolean>).data

                checkUser(
                    loginViewModel,
                    configEvent!!.username!!,
                    configEvent!!.user_code!!,
                    firebaseToken
                )
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (configRoomState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (configRoomState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(loginCheckUserState) {
        when (loginCheckUserState) {
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

                val checkUser: LoginCheckUser =
                    (loginCheckUserState as Result.Success<LoginCheckUser>).data

                loginRefreshUserData?.response?.user_details?.firebase_topics.takeIf { !it.isNullOrEmpty() }
                    ?.let { topics ->
                        subscribeToTopics(topics)
                    }

                val features = checkUser.response?.features
                val uses = checkUser.response?.featuresUsed

                if (features == null || uses == null) {
                    onNavigateDashboard()
                    return@LaunchedEffect
                }
                checkOtpFeatures(
                    features,
                    uses,
                    onNavigateLoginVerifyOTP = {
                        onNavigateLoginVerifyOTP(
                            configEvent!!.username!!,
                            configEvent!!.user_code!!
                        )
                    },
                    onNavigateMPINSet = { onNavigateMPINSet() },
                    onNavigateMPINVerify = { onNavigateMPINVerify() },
                    onNavigateDashboard = { onNavigateDashboard() })
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (loginCheckUserState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (loginCheckUserState as Result.Error).error.message
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
            }
        )
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

private fun checkUser(
    loginViewModel: LoginViewModel,
    username: String,
    appLoginCode: String,
    fcmToken: String
) {
    loginViewModel.getLoginCheckUser(
        LoginCheckUserDataRequest(username, appLoginCode, fcmToken)
    )
}