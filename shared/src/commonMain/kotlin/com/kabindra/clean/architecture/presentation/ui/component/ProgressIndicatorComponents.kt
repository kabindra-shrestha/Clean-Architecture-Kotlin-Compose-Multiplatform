package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier.fillMaxWidth(),
    isCircular: Boolean = false,
    progress: Float? = null,
    useExpressive: Boolean = true,
) {
    if (isCircular) {
        if (useExpressive) {
            ExpressiveCircularProgressIndicator(
                modifier = modifier,
                progress = progress,
            )
        } else if (progress == null) {
            CircularProgressIndicator(modifier = modifier)
        } else {
            CircularProgressIndicator(
                modifier = modifier,
                progress = { progress },
            )
        }
    } else {
        if (useExpressive) {
            ExpressiveLinearProgressIndicator(
                modifier = modifier,
                progress = progress,
            )
        } else if (progress == null) {
            LinearProgressIndicator(modifier = modifier)
        } else {
            LinearProgressIndicator(
                modifier = modifier,
                progress = { progress },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpressiveCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progress: Float? = null,
    strokeWidth: Dp = 2.sdp,
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    val stroke = remember(strokeWidthPx) { Stroke(width = strokeWidthPx, cap = StrokeCap.Round) }

    if (progress == null) {
        CircularWavyProgressIndicator(
            modifier = modifier,
            color = color,
            trackColor = trackColor,
            stroke = stroke,
            trackStroke = stroke,
        )
    } else {
        CircularWavyProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = modifier,
            color = color,
            trackColor = trackColor,
            stroke = stroke,
            trackStroke = stroke,
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpressiveLinearProgressIndicator(
    modifier: Modifier = Modifier.fillMaxWidth(),
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progress: Float? = null,
    strokeWidth: Dp = 2.sdp,
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    val stroke = remember(strokeWidthPx) { Stroke(width = strokeWidthPx, cap = StrokeCap.Round) }

    if (progress == null) {
        LinearWavyProgressIndicator(
            modifier = modifier,
            color = color,
            trackColor = trackColor,
            stroke = stroke,
            trackStroke = stroke,
        )
    } else {
        LinearWavyProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = modifier,
            color = color,
            trackColor = trackColor,
            stroke = stroke,
            trackStroke = stroke,
        )
    }
}

@Composable
fun LoadingDialog(
    isVisible: Boolean = false,
    message: String = "",
    useExpressive: Boolean = true,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        ) {
            CardComponent(
                modifier = Modifier.size(120.sdp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(120.sdp),
                ) {
                    Spacer(modifier = Modifier.height(10.sdp))

                    if (useExpressive) {
                        ExpressiveCircularProgressIndicator(
                            modifier = Modifier.size(36.sdp),
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(36.sdp),
                        )
                    }

                    if (message.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.sdp))
                        TextComponent(
                            text = message,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.sdp))
                }
            }
        }
    }
}
