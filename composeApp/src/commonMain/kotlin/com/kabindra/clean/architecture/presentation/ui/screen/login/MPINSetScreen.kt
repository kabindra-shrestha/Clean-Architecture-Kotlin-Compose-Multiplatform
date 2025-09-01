package com.kabindra.clean.architecture.presentation.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.data.request.MPINSetDataRequest
import com.kabindra.clean.architecture.domain.entity.MPINSet
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.ButtonText
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.OTPField
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
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
fun MPINSetScreen(
    mPINViewModel: MPINViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onNavigateLogin: () -> Unit,
    onNavigateDashboard: () -> Unit,
    onBackNavigate: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val mPINSetState by mPINViewModel.mPINSetState.collectAsState()

    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var successType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }

    var setMPINField by remember { mutableStateOf("") }
    var isSetMPINFieldValid by remember { mutableStateOf(true) }
    var setMPINFieldError by remember { mutableStateOf("") }

    var confirmMPINField by remember { mutableStateOf("") }
    var isConfirmMPINFieldValid by remember { mutableStateOf(true) }
    var confirmMPINFieldError by remember { mutableStateOf("") }

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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
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
                text = "Setup MPIN",
                type = TextType.Headline,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            TextComponent(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingSmall)
                    .align(Alignment.CenterHorizontally),
                text = "MPIN is used for login",
                size = TextSize.Large,
                textAlign = TextAlign.Center
            )

            TextComponent(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge)
                    .align(Alignment.CenterHorizontally),
                text = "Enter MPIN",
                textAlign = TextAlign.Center
            )
            OTPField(
                modifier = Modifier.focusRequester(focusRequester)
                    .align(Alignment.CenterHorizontally),
                value = setMPINField,
                onValueChange = { it, _ ->
                    setMPINField = it.trim()

                    /*if (setMPINField.isBlank()) {
                        isSetMPINFieldValid = false
                        setMPINFieldError = "MPIN must not be empty"
                    } else if (setMPINField.length < 4) {
                        isSetMPINFieldValid = false
                        setMPINFieldError = "MPIN must be 4 characters long"
                        *//*} else if (!otpField.matches("^[a-zA-Z0-9]*$".toRegex())) {
                            isOTPFieldValid = false
                            otpFieldError = "OTP code must be alphanumeric"*//*
                    } else {
                        isSetMPINFieldValid = true
                        setMPINFieldError = ""
                    }*/
                },
                isValueInvalid = !isSetMPINFieldValid,
                isError = isSetMPINFieldValid,
                errorText = setMPINFieldError,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                cellsCount = 4,
                autoFocusByDefault = false
            )

            TextComponent(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingSmall)
                    .align(Alignment.CenterHorizontally),
                text = "Confirm MPIN",
                textAlign = TextAlign.Center
            )

            OTPField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = confirmMPINField,
                onValueChange = { it, _ ->
                    confirmMPINField = it.trim()

                    /*if (confirmMPINField.isBlank()) {
                        isConfirmMPINFieldValid = false
                        confirmMPINFieldError = "Confirm MPIN must not be empty"
                    } else if (confirmMPINField.length < 4) {
                        isConfirmMPINFieldValid = false
                        confirmMPINFieldError = "Confirm MPIN must be 4 characters long"
                    } else if (confirmMPINField != setMPINField) {
                        isConfirmMPINFieldValid = false
                        confirmMPINFieldError = "Confirm MPIN must match New MPIN"
                    } else {
                        isConfirmMPINFieldValid = true
                        confirmMPINFieldError = ""
                    }*/
                },
                isValueInvalid = !isConfirmMPINFieldValid,
                isError = isConfirmMPINFieldValid,
                errorText = confirmMPINFieldError,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                cellsCount = 4,
                autoFocusByDefault = false
            )


            ButtonText(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppTheme.dimens.paddingLarge),
                text = "Continue",
                enabled = !showLoading,
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    if (setMPINField.isBlank()) {
                        isSetMPINFieldValid = false
                        setMPINFieldError = "MPIN must not be empty"
                    } else if (setMPINField.length < 4) {
                        isSetMPINFieldValid = false
                        setMPINFieldError = "MPIN must be 4 characters long"
                    } else if (!setMPINField.matches("^[a-zA-Z0-9]*$".toRegex())) {
                        isSetMPINFieldValid = false
                        setMPINFieldError = "MPIN must be alphanumeric"
                    } else {
                        isSetMPINFieldValid = true
                        setMPINFieldError = ""
                    }
                    if (confirmMPINField.isBlank()) {
                        isConfirmMPINFieldValid = false
                        confirmMPINFieldError = "Confirm MPIN must not be empty"
                    } else if (confirmMPINField.length < 4) {
                        isConfirmMPINFieldValid = false
                        confirmMPINFieldError = "Confirm MPIN must be 4 characters long"
                    } else if (confirmMPINField != setMPINField) {
                        isConfirmMPINFieldValid = false
                        confirmMPINFieldError = "Confirm MPIN must match New MPIN"
                    } else {
                        isConfirmMPINFieldValid = true
                        confirmMPINFieldError = ""
                    }

                    if (isSetMPINFieldValid && isConfirmMPINFieldValid) {
                        mPINSet(mPINViewModel, setMPINField)
                    }
                }
            )

            if (showLoading) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    isCircular = true
                )
            }
        }
    }

    LaunchedEffect(mPINSetState) {
        when (mPINSetState) {
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

                val mPINSet: MPINSet =
                    (mPINSetState as Result.Success<MPINSet>).data

                successMessage = mPINSet.message
                successType = ResponseType.MPINSet

            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (mPINSetState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (mPINSetState as Result.Error).error.message
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
                if (successType == ResponseType.MPINSet) {
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

private fun mPINSet(
    mPINViewModel: MPINViewModel, mpin: String,
) {
    mPINViewModel.getMPINSet(
        MPINSetDataRequest(
            mpin
        )
    )
}