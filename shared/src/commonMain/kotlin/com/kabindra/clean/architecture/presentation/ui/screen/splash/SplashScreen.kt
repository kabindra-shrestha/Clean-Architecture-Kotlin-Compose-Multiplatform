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
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.viewmodel.remote.SplashEvent
import com.kabindra.clean.architecture.presentation.viewmodel.remote.SplashViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_TITLE_VERSION_CHECK
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_VERSION_CHECK
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import org.koin.compose.viewmodel.koinViewModel

private var firebaseToken = ""

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
    var isForcedUpdate by remember { mutableStateOf(false) }
    var userInfo: User? = null
    var userData by remember { mutableStateOf(userInfo) }

    var showUpdateAvailable by remember { mutableStateOf(false) }
    var updateAvailableAction by remember { mutableStateOf(false) }
    var updateAvailableTitle by remember { mutableStateOf("") }
    var updateAvailableMessage by remember { mutableStateOf("") }
    var showUpdateDownload by remember { mutableStateOf(false) }
    var updateDownloadAction by remember { mutableStateOf(false) }
    var updateDownloadMessage by remember { mutableStateOf("") }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
            splashViewModel.resetStates()
        }
    }

    println("isConnected: $isConnected")
    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = -1,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            onDismiss = {
                splashViewModel.resetStates()
            },
        )
        return
    }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val token = getToken()
                if (token != null) {
                    println("Firebase Token: SplashScreen $token")
                    firebaseToken = token
                } else {
                    println("Firebase Token fetch failed")
                }
            } catch (e: Exception) {
                println("Firebase Error fetching token: ${e.message}")
            }
        }
    }

    LaunchedEffect(Unit) {
        checkUpdate(
            isForcedUpdate = isForcedUpdate,
            onReceiveVersionCode = { code: Int -> },
            onReceiveStalenessDays = { days: Int -> },
            onUpdateAvailable = {
                showUpdateAvailable = true
                updateAvailableAction = true
                updateAvailableTitle = ERROR_TITLE_VERSION_CHECK
                updateAvailableMessage = ERROR_VERSION_CHECK

                isForcedUpdate = it
            },
            onUpdateNotAvailable = {
                // Proceed app
                splashViewModel.onEvent(SplashEvent.GetIsLogged)
            },
            onCancelled = {
                if (isForcedUpdate) {
                    // Exit app
                    exitApp()
                } else {
                    // Proceed app
                    splashViewModel.onEvent(SplashEvent.GetIsLogged)
                }
            },
            onFailed = {
                if (isForcedUpdate) {
                    // Exit app
                    exitApp()
                } else {
                    // Proceed app
                    splashViewModel.onEvent(SplashEvent.GetIsLogged)
                }
            },
            onDownloadProgress = { bytesDownloaded: Long, totalBytes: Long -> },
            onDownloadStart = {
                showUpdateDownload = true
                updateDownloadAction = false
                updateDownloadMessage = "Please wait! Downloading update ....."
            },
            onDownloadComplete = {
                showUpdateDownload = true
                updateDownloadAction = true
                updateDownloadMessage = "An update has just been downloaded."
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AppIcon(
            modifier = Modifier
                .width(120.sdp)
                .height(120.sdp)
                .align(Alignment.Center)
        )

        if (splashState.isLoading) {
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.sdp,
                        end = 12.sdp
                    )
                    .align(Alignment.Center)
                    .offset(y = 90.sdp)
            )
        }

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset(y = (-45).sdp),
            text = "Version: ${getPlatform().appVersion}",
            textAlign = TextAlign.Center
        )

        /*AppBrandIcon(
            modifier = Modifier.wrapContentWidth().wrapContentHeight().align(Alignment.BottomCenter)
        )*/
    }

    if (splashState.isLogged == true) {
        splashViewModel.onEvent(SplashEvent.GetUser)
    } else {
        // onNavigateLogin()
        onNavigateDashboard()
    }

    LaunchedEffect(splashState.user) {
        splashState.user?.firebase_topics?.takeIf { it.isNotEmpty() }?.let { topics ->
            unsubscribeFromTopics(topics)
        }

        splashViewModel.onEvent(
            SplashEvent.GetLoginRefreshUserDetails(
                LoginRefreshUserDetailsDataRequest(firebaseToken)
            )
        )
    }

    LaunchedEffect(splashState.loginRefreshUserDetails) {
        splashState.loginRefreshUserDetails?.response?.user_details?.firebase_topics.takeIf { !it.isNullOrEmpty() }
            ?.let { topics ->
                subscribeToTopics(topics)
            }

        val features = splashState.loginRefreshUserDetails?.response?.features
        val uses = splashState.loginRefreshUserDetails?.response?.featuresUsed

        if (features == null || uses == null) {
            onNavigateDashboard()
            return@LaunchedEffect
        }

        // Go To Home Screen
    }

    if (splashState.isSuccess) {
        GlobalSuccessDialog(
            isVisible = true,
            isAction = true,
            message = splashState.successMessage,
            onDismiss = { })
    }

    if (splashState.isError) {
        GlobalErrorDialog(
            isVisible = true,
            isAction = true,
            statusCode = splashState.errorStatusCode,
            title = splashState.errorTitle,
            message = splashState.errorMessage,
            onDismiss = {
                splashViewModel.onEvent(SplashEvent.GetIsLogged)
            },
            onNavigateLogin = { onNavigateLogin() })
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

                completeUpdate("", "")
            },
            onLater = {
                showUpdateAvailable = false
                updateAvailableAction = false
                updateDownloadMessage = ""

                isForcedUpdate = false

                splashViewModel.onEvent(SplashEvent.GetIsLogged)
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

                completeUpdate("", "")
            }
        )
    }
}