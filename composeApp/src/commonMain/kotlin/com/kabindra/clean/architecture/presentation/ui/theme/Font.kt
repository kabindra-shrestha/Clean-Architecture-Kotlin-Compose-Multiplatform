package com.kabindra.clean.architecture.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import composemultiplatformcleanarchitecture.composeapp.generated.resources.Res
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_bold
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_extraBold
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_extraLight
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_light
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_medium
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_regular
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_semiBold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ManRopeFontFamily() = FontFamily(
    Font(Res.font.manrope_light, weight = FontWeight.Light),
    Font(Res.font.manrope_regular, weight = FontWeight.Normal),
    Font(Res.font.manrope_medium, weight = FontWeight.Medium),
    Font(Res.font.manrope_semiBold, weight = FontWeight.SemiBold),
    Font(Res.font.manrope_bold, weight = FontWeight.Bold),
    Font(Res.font.manrope_extraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.manrope_extraLight, weight = FontWeight.ExtraLight)
)

@Composable
fun ManRopeTypography() = Typography().run {

    val fontFamily = ManRopeFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}