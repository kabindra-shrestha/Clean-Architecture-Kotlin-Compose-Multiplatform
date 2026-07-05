package com.kabindra.clean.architecture.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val primaryLight = Color(0xFF0D9488)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFF008378)
val onPrimaryContainerLight = Color(0xFFF4FFFC)
val secondaryLight = Color(0xFF0D9488)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFF008378)
val onSecondaryContainerLight = Color(0xFFF4FFFC)
val tertiaryLight = Color(0xFF0D9488)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFF008378)
val onTertiaryContainerLight = Color(0xFFF4FFFC)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFF93000A)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFF9F9F9)
val onBackgroundLight = Color(0xFF000000)
val surfaceLight = Color(0xFFFCF8F8)
val onSurfaceLight = Color(0xFF000000)
val surfaceVariantLight = Color(0xFFE0E3E3)
val onSurfaceVariantLight = Color(0xFF444748)
val outlineLight = Color(0xFFC5C5C5)
val outlineVariantLight = Color(0xFF636363)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF313030)
val inverseOnSurfaceLight = Color(0xFFF4F0EF)
val inversePrimaryLight = Color(0xFF6BD8CB)
val surfaceDimLight = Color(0xFFDDD9D9)
val surfaceBrightLight = Color(0xFFFCF8F8)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF6F3F2)
val surfaceContainerLight = Color(0xFFF1EDEC)
val surfaceContainerHighLight = Color(0xFFEBE7E7)
val surfaceContainerHighestLight = Color(0xFFE5E2E1)


// Primary colors for prominent UI elements
val primaryDark = Color(0xFFFFFFFF) // White
val onPrimaryDark = Color(0xFF000000) // Black text/icons on primary
val primaryContainerDark = Color(0xFF333333) // Dark gray container
val onPrimaryContainerDark = Color(0xFFFFFFFF) // White text/icons on container

// Secondary colors for supporting elements
val secondaryDark = Color(0xFFCCCCCC) // Light gray
val onSecondaryDark = Color(0xFF000000) // Black text/icons on secondary
val secondaryContainerDark = Color(0xFF444444) // Medium dark gray container
val onSecondaryContainerDark = Color(0xFFFFFFFF) // White text/icons on secondary container

// Tertiary colors for less prominent UI elements
val tertiaryDark = Color(0xFF888888) // Medium gray
val onTertiaryDark = Color(0xFF000000) // Black text/icons on tertiary
val tertiaryContainerDark = Color(0xFF555555) // Dark gray container
val onTertiaryContainerDark = Color(0xFFFFFFFF) // White text/icons on tertiary container

// Error colors
val errorDark = Color(0xFFB71C1C) // Darker red for error color
val onErrorDark = Color(0xFFFF5733) // Black text/icons on error
val errorContainerDark = Color(0xFF7F0000) // Darker red for error container
val onErrorContainerDark = Color(0xFFFF5733) // White text/icons on error container

// Background and surface colors
val backgroundDark = Color(0xFF000000) // Black background
val onBackgroundDark = Color(0xFFFFFFFF) // White text/icons on background
val surfaceDark = Color(0xFF121212) // Very dark gray surface
val onSurfaceDark = Color(0xFFFFFFFF) // White text/icons on surface
val surfaceVariantDark = Color(0xFF333333) // Dark gray variant surface
val onSurfaceVariantDark = Color(0xFFFFFFFF) // White text/icons on variant

// Miscellaneous
val outlineDark = Color(0xFF666666) // Dark gray outline
val outlineVariantDark = Color(0xFF444444) // Variant dark gray outline
val scrimDark = Color(0xFF000000) // Black scrim
val inverseSurfaceDark = Color(0xFFFFFFFF) // White inverse surface
val inverseOnSurfaceDark = Color(0xFF000000) // Black text/icons on inverse
val inversePrimaryDark = Color(0xFF000000) // Black for inverse primary
val surfaceDimDark = Color(0xFF222222) // Dim dark surface
val surfaceBrightDark = Color(0xFF444444) // Bright dark gray surface
val surfaceContainerLowestDark = Color(0xFF000000) // Pure black
val surfaceContainerLowDark = Color(0xFF111111) // Very dark gray
val surfaceContainerDark = Color(0xFF1A1A1A) // Dark gray
val surfaceContainerHighDark = Color(0xFF333333) // Medium dark gray
val surfaceContainerHighestDark = Color(0xFF444444) // Light dark gray

val transparent = Color.Transparent
val unspecified = Color.Unspecified
val overlay = Color(0xFF000000)
val drawerBackground = Color(0xFF1F2937)
val cardBorder = Color(0xFF374151)
val imageBorder = Color(0xFF374151)
val divider = Color(0xFFD8D8D8)
val iconColorPrimary = Color(0xFF0D9488)
val inputFieldDefault = Color(0xFFC5C5C5)
val inputFieldActive = Color(0xFF0D9488)
val inputFieldError = Color(0xFFBA1A1A)
val inputFieldTextDefault = Color(0xFF636363)
val inputFieldTextError = Color(0xFF93000A)
val inputFieldLabelDefault = Color(0xFF636363)
val inputFieldLabelError = Color(0xFFBA1A1A)
val carouselSelected = Color(0xFF0D9488)
val carouselUnselected = Color(0xFFC5C5C5)
val drawerBackgroundSelected = Color(0xFF0D9488)
val drawerBackgroundUnselected = transparent
val drawerTextSelected = Color(0xFFC5C5C5)
val drawerTextUnselected = Color(0xFF0D9488)
val tabSelected = Color(0xFF0D9488)
val tabUnselected = Color(0xFFC5C5C5)
val textSelected = Color(0xFFFFFFFF)
val textUnselected = Color(0xFF636363)

// Light Theme Colors
val customLight = Color(0xFFB7FFD2)

// Dark Theme Colors
val customDark = Color(0xFFB7FFD2)

@Composable
fun custom() = if (isSystemInDarkTheme()) customDark else customLight