package com.kabindra.clean.architecture.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import network.chaintech.sdpcomposemultiplatform.sdp

interface Dimensions {
    val paddingTooSmall: Dp
    val paddingExtraSmall: Dp
    val paddingSmall: Dp
    val paddingNormal: Dp
    val paddingLarge: Dp
    val paddingExtraLarge: Dp
    val minButtonWidth: Dp
    val bottomNavigationPadding: Dp
}

@Composable
fun createDimensions(): Dimensions {
    return object : Dimensions {
        override val paddingTooSmall: Dp = 2.sdp
        override val paddingExtraSmall: Dp = 4.sdp
        override val paddingSmall: Dp = 8.sdp
        override val paddingNormal: Dp = 16.sdp
        override val paddingLarge: Dp = 24.sdp
        override val paddingExtraLarge: Dp = 32.sdp
        override val minButtonWidth: Dp = 120.sdp
        override val bottomNavigationPadding: Dp = 80.sdp
    }
}