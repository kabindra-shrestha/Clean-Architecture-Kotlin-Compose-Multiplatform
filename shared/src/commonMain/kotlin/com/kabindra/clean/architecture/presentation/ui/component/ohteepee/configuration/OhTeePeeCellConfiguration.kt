package com.kabindra.clean.architecture.presentation.ui.component.ohteepee.configuration

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import network.chaintech.sdpcomposemultiplatform.sdp

data class OhTeePeeCellConfiguration(
    val shape: Shape,
    val backgroundColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val textStyle: TextStyle,
    val placeHolderTextStyle: TextStyle,
) {
    companion object {
        @Composable
        fun withDefaults(
            shape: Shape = MaterialTheme.shapes.medium,
            backgroundColor: Color = LocalContentColor.current,
            borderColor: Color = LocalContentColor.current,
            borderWidth: Dp = 1.sdp,
            textStyle: TextStyle = TextStyle(),
            placeHolderTextStyle: TextStyle = textStyle,
        ) = OhTeePeeCellConfiguration(
            shape = shape,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            borderWidth = borderWidth,
            textStyle = textStyle,
            placeHolderTextStyle = placeHolderTextStyle,
        )
    }
}
