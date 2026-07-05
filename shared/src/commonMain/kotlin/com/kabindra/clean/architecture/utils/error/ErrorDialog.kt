@file:OptIn(ExperimentalResourceApi::class)

package com.kabindra.clean.architecture.utils.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.kabindra.clean.architecture.presentation.ui.component.ButtonText
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerLottie
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_CODE_NOT_HANDLED
import com.kabindra.clean.architecture.utils.handler.HandleResponseStatusCode
import composemultiplatformcleanarchitecture.shared.generated.resources.Res
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun GlobalErrorDialog(
    isVisible: Boolean = false,
    isAction: Boolean = false,
    statusCode: Int = -1,
    title: String,
    message: String,
    onDismiss: () -> Unit = {},
    onNavigateLogin: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(isVisible) }

    if (openDialog.value) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/error.json").decodeToString()
            )
        }

        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.sdp),
                shape = RoundedCornerShape(16.sdp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(2.sdp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageHandlerLottie(
                        modifier = Modifier.width(100.sdp).height(100.sdp),
                        image = composition,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.sdp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(12.sdp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = message,
                        textAlign = TextAlign.Center,
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(16.sdp))
                    if (isAction) {
                        Row(
                            modifier = Modifier
                                .padding(2.sdp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonText(
                                modifier = Modifier.align(Alignment.CenterVertically)
                                    .width(100.sdp),
                                text = "Ok",
                                onClick = {
                                    openDialog.value = false

                                    val statusCodeHandler = HandleResponseStatusCode(
                                        statusCode,
                                        onNavigateLogin = { onNavigateLogin() }
                                    ).statusCodeHandler()

                                    if (statusCodeHandler == STATUS_CODE_NOT_HANDLED) {
                                        onDismiss()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServerMaintenanceDialog(
    isVisible: Boolean = false,
    isAction: Boolean = false,
    title: String,
    message: String,
    onDismiss: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(isVisible) }

    if (openDialog.value) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/warning.json").decodeToString()
            )
        }

        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.sdp),
                shape = RoundedCornerShape(16.sdp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(2.sdp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageHandlerLottie(
                        modifier = Modifier.width(100.sdp).height(100.sdp),
                        image = composition,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.sdp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.sdp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = message,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(16.sdp))
                    if (isAction) {
                        Row(
                            modifier = Modifier
                                .padding(2.sdp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonText(
                                modifier = Modifier.align(Alignment.CenterVertically)
                                    .width(100.sdp),
                                text = "Ok",
                                onClick = {
                                    openDialog.value = false

                                    onDismiss()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VersionCheckDialog(
    isVisible: Boolean = false,
    isAction: Boolean = false,
    isForceUpdate: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit = {},
    onUpdate: () -> Unit = {},
    onLater: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(isVisible) }

    if (openDialog.value) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/warning.json").decodeToString()
            )
        }

        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.sdp),
                shape = RoundedCornerShape(16.sdp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(2.sdp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageHandlerLottie(
                        modifier = Modifier.width(100.sdp).height(100.sdp),
                        image = composition,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.sdp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.sdp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = message,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(16.sdp))
                    if (isAction) {
                        Row(
                            modifier = Modifier
                                .padding(2.sdp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonText(
                                modifier = Modifier.align(Alignment.CenterVertically)
                                    .width(100.sdp),
                                text = "Update",
                                onClick = {
                                    openDialog.value = false

                                    onUpdate()
                                }
                            )
                            if (!isForceUpdate) {
                                ButtonText(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                        .width(100.sdp),
                                    text = "Later",
                                    onClick = {
                                        openDialog.value = false

                                        onLater()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}