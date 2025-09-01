package com.kabindra.clean.architecture.presentation.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.ButtonIconAndText
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.component.PasswordField
import com.kabindra.clean.architecture.presentation.ui.component.TextSmall
import com.kabindra.clean.architecture.presentation.ui.component.TextTitleSmall
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog

private lateinit var username: String
private lateinit var appLoginCode: String

@Composable
fun LoginScreen(
    onNavigateLogin: () -> Unit,
    onNavigateForgetPassword: () -> Unit,
    onNavigateLoginQrScanner: () -> Unit,
) {

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()

    var showSuccess by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var userNameField by remember { mutableStateOf("") }
    var isUserNameFieldValid by remember { mutableStateOf(false) }
    var userNameFieldError by remember { mutableStateOf("") }

    var passwordField by remember { mutableStateOf("") }
    var isPasswordFieldValid by remember { mutableStateOf(false) }
    var passwordFieldError by remember { mutableStateOf("") }

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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
            ) {


                AppIcon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (100).dp)

                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 25.dp
                    ),
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    TextTitleSmall(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingSmall),
                        text = "Login"
                    )
                    TextSmall(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingTooSmall),
                        text = "Please sign to continue"
                    )

                    InputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppTheme.dimens.paddingSmall),
                        value = userNameField,
                        onValueChange = {
                            userNameField = it
                            if (userNameField.isBlank()) {
                                isUserNameFieldValid = false
                                userNameFieldError = "Username must not be empty"
                            } else {
                                isUserNameFieldValid = true
                                userNameFieldError = ""
                            }
                        },
                        label = "Username",
                        isError = isUserNameFieldValid,
                        errorText = userNameFieldError,
                        imeAction = ImeAction.Next,
                        onClickTrailingIcon = {}
                    )

                    PasswordField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppTheme.dimens.paddingSmall),
                        value = passwordField,
                        onValueChange = {
                            passwordField = it
                            if (passwordField.isBlank()) {
                                isPasswordFieldValid = false
                                passwordFieldError = "Password must not be empty"
                            } else {
                                isPasswordFieldValid = true
                                passwordFieldError = ""
                            }
                        },
                        label = "Password",
                        isError = isPasswordFieldValid,
                        errorText = passwordFieldError,
                        imeAction = ImeAction.Next
                    )

                    TextTitleSmall(
                        modifier = Modifier
                            .padding(top = AppTheme.dimens.paddingTooSmall)
                            .align(Alignment.End)
                            .clickable {
                                onNavigateForgetPassword()
                            },
                        text = "Forget Password?",
                        textAlign = TextAlign.End
                    )

                    ButtonTextAndIcon(
                        modifier = Modifier.fillMaxWidth(),
                        iconVector = Icons.AutoMirrored.Outlined.ArrowRight,
                        iconContentDescription = "Login",
                        text = "Login",
                        onClick = {
                            onNavigateLoginQrScanner()
                        })

                    ButtonIconAndText(
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        iconVector = Icons.AutoMirrored.Outlined.ArrowRight,
                        iconContentDescription = "Tap to login with fingerprint",
                        text = "Tap to login with fingerprint",
                        onClick = {
                        })
                }
            }
        }
    }

    if (showSuccess) {
        GlobalSuccessDialog(
            isVisible = showSuccess,
            isAction = true,
            message = successMessage,
            onDismiss = {}
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