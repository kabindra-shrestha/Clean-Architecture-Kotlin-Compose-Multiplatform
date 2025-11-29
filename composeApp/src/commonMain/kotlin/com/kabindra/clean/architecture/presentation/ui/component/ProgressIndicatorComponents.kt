package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier.fillMaxWidth(),
    isCircular: Boolean = false,
    progress: Float? = null
) {
    if (isCircular) {
        if (progress == null) {
            CircularProgressIndicator(
                modifier = modifier,
            )
        } else {
            CircularProgressIndicator(
                modifier = modifier,
                progress = { progress },
            )
        }
    } else {
        if (progress == null) {
            LinearProgressIndicator(
                modifier = modifier,
            )
        } else {
            LinearProgressIndicator(
                modifier = modifier,
                progress = { progress },
            )
        }
    }
}

@Composable
fun LoadingDialog(
    isVisible: Boolean = false,
    message: String = ""
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CardBorderInside(
                modifier = Modifier
                    .size(200.sdp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(60.sdp)
                    )
                    if (message.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.sdp))
                        TextComponent(
                            text = message,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}