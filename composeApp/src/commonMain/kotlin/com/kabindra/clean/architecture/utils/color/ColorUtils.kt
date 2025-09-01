package com.kabindra.clean.architecture.utils.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kabindra.clean.architecture.presentation.ui.theme.transparent

@Composable
fun gradientTopAppBarDashboard(
    darkTheme: Boolean = isSystemInDarkTheme()
): Brush {
    return if (darkTheme) gradientTopAppBarDashboardDark else gradientTopAppBarDashboardLight
}

val gradientTopAppBarDashboardLight = Brush.verticalGradient(
    0f to Color(0xE6FFFFFF),  // 90% black
    0.5f to Color(0xCCFFFFFF),  // 80% black
    0.75f to Color(0x80FFFFFF),  // 50% black
    0.9f to Color(0x33FFFFFF),  // 20% black
    1f to transparent   // Transition to transparent at the bottom
)

val gradientTopAppBarDashboardDark = Brush.verticalGradient(
    0f to Color(0xE6000000),  // 90% black
    0.5f to Color(0xCC000000),  // 80% black
    0.75f to Color(0x80000000),  // 50% black
    0.9f to Color(0x33000000),  // 20% black
    1f to transparent   // Transition to transparent at the bottom
)