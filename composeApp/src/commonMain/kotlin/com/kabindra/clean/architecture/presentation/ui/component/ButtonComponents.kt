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
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val buttonShape = RoundedCornerShape(
        topStart = 10.dp,
        topEnd = 10.dp,
        bottomEnd = 10.dp,
        bottomStart = 10.dp
    )

    if (isOutlined) {
        OutlinedButton(
            modifier = modifier,
            // .requiredWidth(AppTheme.dimens.minButtonWidth)
            // .then(modifier),
            enabled = enabled,
            colors = buttonColors,
            shape = buttonShape,
            onClick = { onClick() }
        ) {
            content()
        }
    } else {
        Button(
            modifier = modifier,
            // .requiredWidth(AppTheme.dimens.minButtonWidth)
            // .then(modifier),
            enabled = enabled,
            colors = buttonColors,
            shape = buttonShape,
            onClick = { onClick() }
        ) {
            content()
        }
    }
}

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
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
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        ImageHandlerVector(
            modifier = Modifier
                .size(20.dp)
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
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        ImageHandlerVector(
            modifier = Modifier
                .size(20.dp)
                .aspectRatio(1f / 1f),
            image = iconVector,
            contentDescription = iconContentDescription
        )
        Spacer(modifier = Modifier.width(2.dp))
        TextButtonAction(
            modifier = Modifier.padding(start = AppTheme.dimens.paddingSmall),
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
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = MutableInteractionSource(),
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
        Spacer(modifier = Modifier.height(2.dp))
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.paddingTooSmall),
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
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    ButtonComponent(
        modifier = modifier,
        enabled = enabled,
        isOutlined = isOutlined,
        buttonColors = buttonColors,
        onClick = { onClick() }
    ) {
        ImageHandlerVector(
            modifier = Modifier
                .size(20.dp)
                .aspectRatio(1f / 1f),
            image = iconVector,
            contentDescription = iconContentDescription
        )
        Spacer(modifier = Modifier.width(2.dp))
        TextButtonAction(
            modifier = Modifier.padding(start = AppTheme.dimens.paddingSmall),
            text = text
        )

    }
}

@Composable
fun ButtonBack(
    modifier: Modifier = Modifier.width(50.dp).height(50.dp),
    onClick: () -> Unit = {}
) {
    IconButton(modifier = modifier.padding(10.dp), onClick = { onClick() }) {
        ImageHandlerVector(
            image = Icons.Default.ArrowCircleLeft,
            contentDescription = "Back Button"
        )
    }
}

@Composable
fun ButtonClose(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilledTonalIconButton(
        modifier = modifier
            .size(24.dp),
        onClick = { onClick() },
        shape = IconButtonDefaults.filledShape
    ) {
        ImageHandlerVector(
            image = Icons.Default.Close,
            contentDescription = "Edit Button"
        )
    }
}

@Composable
fun ButtonAction(
    modifier: Modifier = Modifier,
    iconVector: ImageVector = Icons.Default.EditCalendar,
    iconContentDescription: String = "",
    text: String = "",
    tint: Color = Color(0xFFFF9500),
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        ImageHandlerVector(
            modifier = modifier
                .size(24.dp)
                .aspectRatio(1f / 1f),
            image = iconVector,
            tint = tint,
            contentDescription = iconContentDescription
        )
    }
}