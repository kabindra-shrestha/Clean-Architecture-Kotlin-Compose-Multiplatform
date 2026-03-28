package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import network.chaintech.sdpcomposemultiplatform.sdp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    useExpressiveShapes: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val defaultButtonShape = RoundedCornerShape(
        topStart = 6.sdp,
        topEnd = 6.sdp,
        bottomEnd = 6.sdp,
        bottomStart = 6.sdp,
    )
    val expressiveButtonShapes = ButtonDefaults.shapes()

    if (isOutlined) {
        if (useExpressiveShapes) {
            OutlinedButton(
                modifier = modifier,
                enabled = enabled,
                colors = buttonColors,
                shapes = expressiveButtonShapes,
                onClick = { onClick() }
            ) {
                content()
            }
        } else {
            OutlinedButton(
                modifier = modifier,
                enabled = enabled,
                colors = buttonColors,
                shape = defaultButtonShape,
                onClick = { onClick() }
            ) {
                content()
            }
        }
    } else {
        if (useExpressiveShapes) {
            Button(
                modifier = modifier,
                enabled = enabled,
                colors = buttonColors,
                shapes = expressiveButtonShapes,
                onClick = { onClick() }
            ) {
                content()
            }
        } else {
            Button(
                modifier = modifier,
                enabled = enabled,
                colors = buttonColors,
                shape = defaultButtonShape,
                onClick = { onClick() }
            ) {
                content()
            }
        }
    }
}

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    useExpressiveShapes: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        useExpressiveShapes = useExpressiveShapes,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        TextComponent(modifier = Modifier, text = text, size = TextSize.Medium)
    }
}

@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    iconVector: ImageVector = Icons.Outlined.PlayArrow,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    useExpressiveShapes: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        useExpressiveShapes = useExpressiveShapes,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        ImageHandlerVector(
            modifier = Modifier
                .size(12.sdp)
                .aspectRatio(1f / 1f),
            image = iconVector,
            contentDescription = "Icon Button"
        )
    }
}

@Composable
fun ButtonIconAndText(
    modifier: Modifier = Modifier,
    iconVector: ImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
    iconContentDescription: String = "",
    text: String = "",
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    useExpressiveShapes: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        useExpressiveShapes = useExpressiveShapes,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        ImageHandlerVector(
            modifier = Modifier
                .size(12.sdp)
                .aspectRatio(1f / 1f),
            image = iconVector,
            contentDescription = iconContentDescription
        )
        Spacer(modifier = Modifier.width(1.sdp))
        TextComponent(
            modifier = Modifier.padding(start = 1.sdp),
            text = text
        )
    }
}

@Composable
fun ButtonTopIconAndText(
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    iconVector: ImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
    iconContentDescription: String = "",
    text: String = "",
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(7.sdp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = {
                    onClick()
                }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageHandlerVector(
            modifier = modifierIcon,
            image = iconVector,
            contentDescription = iconContentDescription
        )
        Spacer(modifier = Modifier.height(1.sdp))
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.sdp),
            text = text,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun ButtonTextAndIcon(
    modifier: Modifier = Modifier,
    iconVector: ImageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
    iconContentDescription: String = "",
    text: String = "",
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    useExpressiveShapes: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        useExpressiveShapes = useExpressiveShapes,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        ImageHandlerVector(
            modifier = Modifier
                .size(12.sdp)
                .aspectRatio(1f / 1f),
            image = iconVector,
            contentDescription = iconContentDescription
        )
        Spacer(modifier = Modifier.width(1.sdp))
        TextComponent(
            modifier = Modifier.padding(start = 1.sdp),
            text = text
        )

    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonBack(
    modifier: Modifier = Modifier
        .width(30.sdp)
        .height(30.sdp),
    useExpressiveShapes: Boolean = true,
    onClick: () -> Unit = {}
) {
    if (useExpressiveShapes) {
        IconButton(
            modifier = modifier.padding(6.sdp),
            shapes = IconButtonDefaults.shapes(),
            onClick = { onClick() }
        ) {
            ImageHandlerVector(
                image = Icons.Default.ArrowCircleLeft,
                contentDescription = "Back Button"
            )
        }
    } else {
        IconButton(
            modifier = modifier.padding(6.sdp),
            onClick = { onClick() }) {
            ImageHandlerVector(
                image = Icons.Default.ArrowCircleLeft,
                contentDescription = "Back Button"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonClose(
    modifier: Modifier = Modifier,
    useExpressiveShapes: Boolean = true,
    onClick: () -> Unit
) {
    if (useExpressiveShapes) {
        FilledTonalIconButton(
            modifier = modifier
                .size(14.sdp),
            shapes = IconButtonDefaults.shapes(),
            onClick = { onClick() },
        ) {
            ImageHandlerVector(
                image = Icons.Default.Close,
                contentDescription = "Edit Button"
            )
        }
    } else {
        FilledTonalIconButton(
            modifier = modifier
                .size(14.sdp),
            onClick = { onClick() },
            shape = IconButtonDefaults.filledShape
        ) {
            ImageHandlerVector(
                image = Icons.Default.Close,
                contentDescription = "Edit Button"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonAction(
    modifier: Modifier = Modifier,
    iconVector: ImageVector = Icons.Default.EditCalendar,
    iconContentDescription: String = "",
    text: String = "",
    tint: Color = Color(0xFFFF9500),
    useExpressiveShapes: Boolean = true,
    onClick: () -> Unit
) {
    if (useExpressiveShapes) {
        IconButton(
            shapes = IconButtonDefaults.shapes(),
            onClick = onClick
        ) {
            ImageHandlerVector(
                modifier = modifier
                    .size(14.sdp)
                    .aspectRatio(1f / 1f),
                image = iconVector,
                tint = tint,
                contentDescription = iconContentDescription
            )
        }
    } else {
        IconButton(onClick = onClick) {
            ImageHandlerVector(
                modifier = modifier
                    .size(14.sdp)
                    .aspectRatio(1f / 1f),
                image = iconVector,
                tint = tint,
                contentDescription = iconContentDescription
            )
        }
    }
}
