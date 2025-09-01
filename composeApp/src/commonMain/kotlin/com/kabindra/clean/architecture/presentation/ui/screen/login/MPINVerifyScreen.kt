package com.kabindra.clean.architecture.presentation.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.data.request.MPINVerifyDataRequest
import com.kabindra.clean.architecture.domain.entity.MPINVerify
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.ButtonText
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.OTPField
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextType
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.viewmodel.remote.MPINViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MPINVerifyScreen(
    mPINViewModel: MPINViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onNavigateLogin: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onBackNavigate: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val mPINVerifyState by mPINViewModel.mPINVerifyState.collectAsState()

    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var successType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }

    var verifyMPINField by remember { mutableStateOf("") }
    var isVerifyMPINFieldValid by remember { mutableStateOf(true) }
    var verifyMPINFieldError by remember { mutableStateOf("") }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
            mPINViewModel.resetStates()

            showLoading = false
            showSuccess = false
            showError = false
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

    Box(
        modifier = Modifier.fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppIcon(
                modifier = Modifier.padding(top = 20.dp).width(130.dp).height(130.dp)
                    .align(Alignment.CenterHorizontally)
            )
            TextComponent(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
                    .align(Alignment.CenterHorizontally),
                text = "Enter Your MPIN",
                type = TextType.Headline,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            TextComponent(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
                    .align(Alignment.CenterHorizontally),
                text = "Enter MPIN",
                textAlign = TextAlign.Center
            )
            OTPField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = verifyMPINField,
                onValueChange = { it, _ ->
                    verifyMPINField = it.trim()

                    /*if (verifyMPINField.isBlank()) {
                        isVerifyMPINFieldValid = false
                        verifyMPINFieldError = "MPIN must not be empty"
                    } else if (verifyMPINField.length < 4) {
                        isVerifyMPINFieldValid = false
                        verifyMPINFieldError = "MPIN must be 4 characters long"
                        *//*} else if (!otpField.matches("^[a-zA-Z0-9]*$".toRegex())) {
                            isOTPFieldValid = false
                            otpFieldError = "OTP code must be alphanumeric"*//*
                    } else {
                        isVerifyMPINFieldValid = true
                        verifyMPINFieldError = ""
                    }*/
                },
                isValueInvalid = !isVerifyMPINFieldValid,
                isError = isVerifyMPINFieldValid,
                errorText = verifyMPINFieldError,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                cellsCount = 4,
                autoFocusByDefault = false
            )

            Spacer(modifier = Modifier.height(70.dp))

            ButtonText(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppTheme.dimens.paddingExtraLarge),
                text = "Continue",
                enabled = !showLoading,
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    if (verifyMPINField.isBlank()) {
                        isVerifyMPINFieldValid = false
                        verifyMPINFieldError = "MPIN must not be empty"
                    } else if (verifyMPINField.length < 4) {
                        isVerifyMPINFieldValid = false
                        verifyMPINFieldError = "MPIN must be 4 characters long"
                    } else if (!verifyMPINField.matches("^[a-zA-Z0-9]*$".toRegex())) {
                        isVerifyMPINFieldValid = false
                        verifyMPINFieldError = "MPIN must be alphanumeric"
                    } else {
                        isVerifyMPINFieldValid = true
                        verifyMPINFieldError = ""
                    }
                    if (isVerifyMPINFieldValid) {
                        mPINVerify(mPINViewModel, verifyMPINField)
                    }

                }
            )
            /*Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.paddingNormal),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextMedium(
                    text = "Forgot MPIN? ",
                    textAlign = TextAlign.Center
                )
                TextMedium(
                    text = "RESET MPIN? ",
                    textAlign = TextAlign.Center, fontWeight = FontWeight.Bold
                )
            }*/
            if (showLoading) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    isCircular = true
                )
            }
        }
    }

    LaunchedEffect(mPINVerifyState) {
        when (mPINVerifyState) {
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

                val mPINVerify: MPINVerify =
                    (mPINVerifyState as Result.Success<MPINVerify>).data

                successMessage = mPINVerify.message
                successType = ResponseType.MPINVerify

            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (mPINVerifyState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (mPINVerifyState as Result.Error).error.message
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
                if (successType == ResponseType.MPINVerify) {
                    onNavigateDashboard()
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

private fun mPINVerify(
    mPINViewModel: MPINViewModel,
    mpin: String,
) {
    mPINViewModel.getMPINVerify(
        MPINVerifyDataRequest(
            mpin
        )
    )
}