package com.kabindra.inappupdate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun AppTypography() = Typography().run {
    copy(
        // 57 * 0.6 = 34, 45 * 0.6 = 27, 36 * 0.6 = 22
        displayLarge = displayLarge.copy(fontSize = 34.ssp),
        displayMedium = displayMedium.copy(fontSize = 27.ssp),
        displaySmall = displaySmall.copy(fontSize = 22.ssp),

        // 32 * 0.6 = 19, 28 * 0.6 = 17, 24 * 0.6 = 14
        headlineLarge = headlineLarge.copy(fontSize = 19.ssp),
        headlineMedium = headlineMedium.copy(fontSize = 17.ssp),
        headlineSmall = headlineSmall.copy(fontSize = 14.ssp),

        // 22 * 0.6 = 13, 16 * 0.6 = 10, 14 * 0.6 = 8
        titleLarge = titleLarge.copy(fontSize = 13.ssp),
        titleMedium = titleMedium.copy(fontSize = 10.ssp),
        titleSmall = titleSmall.copy(fontSize = 8.ssp),

        // 16 * 0.6 = 10, 14 * 0.6 = 8, 12 * 0.6 = 7
        bodyLarge = bodyLarge.copy(fontSize = 10.ssp),
        bodyMedium = bodyMedium.copy(fontSize = 8.ssp),
        bodySmall = bodySmall.copy(fontSize = 7.ssp),

        // 14 * 0.6 = 8, 12 * 0.6 = 7, 11 * 0.6 = 7
        labelLarge = labelLarge.copy(fontSize = 8.ssp),
        labelMedium = labelMedium.copy(fontSize = 7.ssp),
        labelSmall = labelSmall.copy(fontSize = 7.ssp),
    )
}