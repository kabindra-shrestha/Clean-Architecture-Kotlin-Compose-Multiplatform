package com.kabindra.clean.architecture.presentation.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.base.ObserveAsEvents
import com.kabindra.clean.architecture.utils.constants.AlertType
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_TITLE_VERSION_CHECK
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_VERSION_CHECK
import com.kabindra.clean.architecture.utils.constants.MessageType
import com.kabindra.clean.architecture.utils.enums.subscribeToTopics
import com.kabindra.clean.architecture.utils.enums.unsubscribeFromTopics
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.getPlatform
import com.kabindra.clean.architecture.utils.getToken
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import com.kabindra.inappupdate.UpdateAvailableDialog
import com.kabindra.inappupdate.UpdateDownloadDialog
import com.kabindra.inappupdate.checkUpdate
import com.kabindra.inappupdate.completeUpdate
import com.kabindra.inappupdate.exitApp
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onNavigateLogin: () -> Unit,
    onNavigateDashboard: () -> Unit
) {
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val splashState by splashViewModel.splashState.collectAsStateWithLifecycle()
    var dialogState by remember { mutableStateOf<SplashEvent.ShowMessage?>(null) }

    var firebaseToken by remember { mutableStateOf<String?>(null) }
    var isFCMTokenReady by remember { mutableStateOf(false) }

    var showUpdateAvailable by remember { mutableStateOf(false) }
    var updateAvailableAction by remember { mutableStateOf(false) }
    var updateAvailableTitle by remember { mutableStateOf("") }
    var updateAvailableMessage by remember { mutableStateOf("") }
    var isForcedUpdate by remember { mutableStateOf(false) }
    var showUpdateDownload by remember { mutableStateOf(false) }
    var updateDownloadAction by remember { mutableStateOf(false) }
    var updateDownloadMessage by remember { mutableStateOf("") }
    var errorStatusCode by remember { mutableStateOf(-1) }

    DisposableEffect(Unit) {
        onDispose {
            splashViewModel.resetStates()
        }
    }

    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = errorStatusCode,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            onDismiss = {
                splashViewModel.resetStates()
            },
        )
        return
    }

    // OPTIMIZED: Fetch FCM Token with shorter delays
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            println("SplashScreen No internet connection, waiting...")
            return@LaunchedEffect
        }

        println("SplashScreen Getting Firebase Token")

        val isIOS = getPlatform().devicePlatform.contains("iOS", ignoreCase = true)

        // Reduced initial delay
        val initialDelay = if (isIOS) 50L else 50L
        delay(initialDelay)

        var retryCount = 0
        val maxRetries = 3 // Reduced from 5 to 3
        var tokenObtained = false

        while (retryCount < maxRetries && !tokenObtained) {
            val token = runCatching { getToken() }
                .getOrNull()
                .orEmpty()

            if (token.isNotEmpty()) {
                println("SplashScreen Firebase Token Retrieved Successfully on attempt ${retryCount + 1}")
                println("SplashScreen Firebase Token: $token")
                firebaseToken = token
                tokenObtained = true
            } else {
                retryCount++
                if (retryCount < maxRetries) {
                    // Shorter delays: 300ms, 600ms, 900ms
                    val retryDelay = (retryCount * 300).toLong()
                    println("SplashScreen Firebase Token is empty, retrying in ${retryDelay}ms (attempt $retryCount/$maxRetries)")
                    delay(retryDelay)
                }
            }
        }

        if (!tokenObtained) {
            println("SplashScreen Failed to get Firebase Token after $maxRetries attempts")
            println("SplashScreen Proceeding without FCM token - user may not receive notifications")
            firebaseToken = "" // Set to empty string to indicate we tried and failed
        }

        // Mark FCM token as ready
        isFCMTokenReady = true
        println("SplashScreen FCM Token Ready: ${firebaseToken?.let { if (it.isEmpty()) "EMPTY" else "PRESENT" } ?: "NULL"}")
    }

    // Check for updates ONLY AFTER FCM token is ready
    LaunchedEffect(isFCMTokenReady) {
        if (!isFCMTokenReady) {
            println("SplashScreen Waiting for FCM token before update check")
            return@LaunchedEffect
        }

        if (!isConnected) {
            println("SplashScreen No internet connection, skipping update check")
            return@LaunchedEffect
        }

        println("SplashScreen Checking for Updates (FCM Token Ready)")
        checkUpdate(
            isForcedUpdate = isForcedUpdate,
            onUpdateAvailable = {
                println("SplashScreen Update Available")
                showUpdateAvailable = true
                updateAvailableAction = true
                updateAvailableTitle = ERROR_TITLE_VERSION_CHECK
                updateAvailableMessage = ERROR_VERSION_CHECK
                isForcedUpdate = it
            },
            onUpdateNotAvailable = {
                println("SplashScreen Update Not Available - GetIsLogged")
                splashViewModel.onAction(SplashAction.GetIsLogged)
            },
            onCancelled = {
                println("SplashScreen Update Check Cancelled")
                if (isForcedUpdate) {
                    exitApp()
                } else {
                    println("SplashScreen Proceeding after cancel - GetIsLogged")
                    splashViewModel.onAction(SplashAction.GetIsLogged)
                }
            },
            onFailed = {
                println("SplashScreen Update Check Failed")
                if (isForcedUpdate) {
                    exitApp()
                } else {
                    println("SplashScreen Proceeding after failure - GetIsLogged")
                    splashViewModel.onAction(SplashAction.GetIsLogged)
                }
            },
            onDownloadStart = {
                println("SplashScreen Update Download Started")
                showUpdateDownload = true
                updateDownloadAction = false
                updateDownloadMessage = "Please wait! Downloading update ....."
            },
            onDownloadComplete = {
                println("SplashScreen Update Download Complete")
                showUpdateDownload = true
                updateDownloadAction = true
                updateDownloadMessage = "An update has just been downloaded."
            },
            onReceiveVersionCode = { code: Int -> println("SplashScreen Version Code: $code") },
            onReceiveStalenessDays = { days: Int -> println("SplashScreen Staleness Days: $days") },
            onDownloadProgress = { bytesDownloaded: Long, totalBytes: Long -> },
        )
    }

    LaunchedEffect(splashState.isLogged) {
        if (splashState.isLogged == null) {
            return@LaunchedEffect
        }

        if (splashState.isLogged == true) {
            println("SplashScreen Checking Login Status - User is Logged")
            println("SplashScreen GetUser")
            splashViewModel.onAction(SplashAction.GetUser)
        } else {
            println("SplashScreen Checking Login Status - User is Not Logged")
            // splashViewModel.onAction(SplashAction.OnNavigateLogin)
            splashViewModel.onAction(SplashAction.OnNavigateDashboard)
        }
    }

    LaunchedEffect(splashState.user) {
        if (splashState.user == null) {
            return@LaunchedEffect
        }

        println("SplashScreen User Retrieved")
        splashState.user?.firebase_topics?.takeIf { it.isNotEmpty() }?.let { topics ->
            println("SplashScreen Unsubscribing from old topics")
            unsubscribeFromTopics(topics)
        }

        val tokenToUse = firebaseToken ?: ""
        println("SplashScreen GetLoginRefreshUserDetails with token: ${if (tokenToUse.isEmpty()) "EMPTY" else "PRESENT"}")

        splashViewModel.onAction(
            SplashAction.GetLoginRefreshUserDetails(
                LoginRefreshUserDetailsDataRequest(tokenToUse)
            )
        )
    }

    LaunchedEffect(splashState.loginRefreshUserDetails) {
        if (splashState.loginRefreshUserDetails == null) {
            return@LaunchedEffect
        }

        println("SplashScreen Login Refresh User Details Retrieved")
        splashState.loginRefreshUserDetails?.response?.user_details?.firebase_topics.takeIf { !it.isNullOrEmpty() }
            ?.let { topics ->
                println("SplashScreen Subscribing to new topics")
                subscribeToTopics(topics)
            }

        val features = splashState.loginRefreshUserDetails?.response?.features
        val uses = splashState.loginRefreshUserDetails?.response?.featuresUsed

        if (features == null || uses == null) {
            println("SplashScreen No features found, navigating to dashboard")
            splashViewModel.onAction(SplashAction.OnNavigateDashboard)
            return@LaunchedEffect
        }

        println("SplashScreen Checking MPin Features")
        splashViewModel.onAction(SplashAction.OnNavigateDashboard)
    }

    ObserveAsEvents(splashViewModel.splashEvent) { event ->
        when (event) {
            is SplashEvent.ShowMessage -> {
                when (event.alertType) {
                    AlertType.None -> {}
                    AlertType.Snackbar -> {}
                    AlertType.Toast -> {}
                    AlertType.Dialog -> {
                        dialogState = event
                    }
                }
            }

            is SplashEvent.OnNavigateLogin -> {
                onNavigateLogin()
            }

            is SplashEvent.OnNavigateDashboard -> {
                onNavigateDashboard()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AppIcon(
            modifier = Modifier.width(120.sdp)
                .height(120.sdp)
                .align(Alignment.Center)
        )

        if (splashState.isLoading || !isFCMTokenReady) {
            LoadingIndicator(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 12.sdp, end = 12.sdp)
                    .align(Alignment.Center)
                    .offset(y = 90.sdp)
            )
        }

        TextComponent(
            text = "Version: ${getPlatform().appVersion}",
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .offset(y = (-42).sdp - 3.sdp),
            textAlign = TextAlign.Center
        )
    }

    dialogState?.let { dialog ->
        when (dialog.messageType) {
            MessageType.Success -> {
                GlobalSuccessDialog(
                    isVisible = true,
                    isAction = true,
                    message = dialog.message,
                    onDismiss = {
                        dialogState = null
                    }
                )
            }

            MessageType.Error -> {
                GlobalErrorDialog(
                    isVisible = true,
                    isAction = true,
                    statusCode = dialog.statusCode,
                    title = dialog.title,
                    message = dialog.message,
                    onDismiss = {
                        println("SplashScreen Retry - GetIsLogged")
                        dialogState = null
                        splashViewModel.onAction(SplashAction.GetIsLogged)
                    },
                    onNavigateLogin = {
                        dialogState = null
                        splashViewModel.onAction(SplashAction.OnNavigateLogin)
                    }
                )
            }

            MessageType.Confirmation -> {}
        }
    }

    if (showUpdateAvailable) {
        UpdateAvailableDialog(
            isVisible = showUpdateAvailable,
            isAction = updateAvailableAction,
            isForceUpdate = isForcedUpdate,
            title = updateAvailableTitle,
            message = updateAvailableMessage,
            onDismiss = {
                showUpdateAvailable = false
                updateAvailableAction = false
                updateDownloadMessage = ""
            },
            onUpdate = {
                showUpdateAvailable = false
                updateAvailableAction = false
                updateDownloadMessage = ""

                completeUpdate("", "https://apps.apple.com/us/app/yak-hrm/id6747244861")
            },
            onLater = {
                showUpdateAvailable = false
                updateAvailableAction = false
                updateDownloadMessage = ""

                isForcedUpdate = false

                println("SplashScreen GetIsLogged")
                splashViewModel.onAction(SplashAction.GetIsLogged)
            }
        )
    }

    if (showUpdateDownload) {
        UpdateDownloadDialog(
            isVisible = showUpdateDownload,
            isAction = updateDownloadAction,
            message = updateDownloadMessage,
            onDismiss = {
                showUpdateDownload = false
                updateDownloadAction = false
                updateDownloadMessage = ""
            },
            onInstall = {
                showUpdateDownload = false
                updateDownloadAction = false
                updateDownloadMessage = ""

                completeUpdate("", "https://apps.apple.com/us/app/yak-hrm/id6747244861")
            }
        )
    }
}