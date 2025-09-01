package com.kabindra.clean.architecture.presentation.ui.screen.forgetpassword

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.component.TextSmall
import com.kabindra.clean.architecture.presentation.ui.component.TextTitleSmall
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme

@Composable
fun ForgetPassword() {
    var userNameField by remember { mutableStateOf("") }
    var isUserNameFieldValid by remember { mutableStateOf(false) }
    var userNameFieldError by remember { mutableStateOf("") }

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
                        text = "Forget Password?"
                    )
                    TextSmall(
                        modifier = Modifier.padding(top = AppTheme.dimens.paddingTooSmall),
                        text = "No problem. Just let us know your username and we will email you a one time password that will allow you to login."
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

                    ButtonTextAndIcon(
                        modifier = Modifier.fillMaxWidth(),
                        iconVector = Icons.AutoMirrored.Outlined.ArrowRight,
                        iconContentDescription = "Request New Password",
                        text = "Request New Password",
                        onClick = {
                        })
                }
            }
        }

    }
}