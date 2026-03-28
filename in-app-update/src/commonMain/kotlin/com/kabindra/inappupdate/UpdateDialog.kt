@file:OptIn(ExperimentalResourceApi::class)

package com.kabindra.inappupdate

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.kabindra.inappupdate.ui.component.ButtonNormal
import com.kabindra.inappupdate.ui.component.ImageHandlerLottie
import com.kabindra.inappupdate.ui.component.TextMedium
import composemultiplatformcleanarchitecture.in_app_update.generated.resources.Res
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun UpdateDownloadDialog(
    isVisible: Boolean = false,
    isAction: Boolean = false,
    message: String,
    onDismiss: () -> Unit = {},
    onInstall: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(isVisible) }

    if (!isAction) {
        // Automatically dismiss the dialog after 5 seconds
        LaunchedEffect(openDialog.value) {
            if (openDialog.value) {
                delay(5000) // 5 seconds delay

                openDialog.value = false

                onDismiss()
            }
        }
    }

    if (openDialog.value) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/success.json").decodeToString()
            )
        }
        val progress by animateLottieCompositionAsState(composition)

        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.sdp),
                shape = RoundedCornerShape(10.sdp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(1.sdp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageHandlerLottie(
                        modifier = Modifier
                            .width(60.sdp)
                            .height(60.sdp),
                        image = composition,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(5.sdp))
                    TextMedium(
                        text = message,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.sdp))
                    if (isAction) {
                        Row(
                            modifier = Modifier
                                .padding(1.sdp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonNormal(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .width(60.sdp),
                                text = "Install",
                                onClick = {
                                    openDialog.value = false

                                    onInstall()
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
fun UpdateAvailableDialog(
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
                    .padding(1.sdp),
                shape = RoundedCornerShape(10.sdp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(1.sdp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageHandlerLottie(
                        modifier = Modifier
                            .width(60.sdp)
                            .height(60.sdp),
                        image = composition,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(5.sdp))
                    TextMedium(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(2.sdp))
                    TextMedium(
                        modifier = Modifier.fillMaxWidth(),
                        text = message,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(10.sdp))
                    if (isAction) {
                        Row(
                            modifier = Modifier
                                .padding(1.sdp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonNormal(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .width(60.sdp),
                                text = "Update",
                                onClick = {
                                    openDialog.value = false

                                    onUpdate()
                                }
                            )
                            if (!isForceUpdate) {
                                ButtonNormal(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .width(60.sdp),
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