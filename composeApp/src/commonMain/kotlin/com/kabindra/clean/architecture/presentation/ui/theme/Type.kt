package com.kabindra.clean.architecture.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import composemultiplatformcleanarchitecture.composeapp.generated.resources.Res
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_bold
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_extra_bold
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_extra_light
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_light
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_medium
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_regular
import composemultiplatformcleanarchitecture.composeapp.generated.resources.manrope_semi_bold
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@Composable
fun AppTypography() = Typography().run {
    val fontFamily = ManRopeFontFamily()

    copy(
        displayLarge = displayLarge.copy(fontSize = 57.ssp, fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontSize = 45.ssp, fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontSize = 36.ssp, fontFamily = fontFamily),

        headlineLarge = headlineLarge.copy(fontSize = 32.ssp, fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontSize = 28.ssp, fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontSize = 24.ssp, fontFamily = fontFamily),

        titleLarge = titleLarge.copy(fontSize = 22.ssp, fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontSize = 16.ssp, fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontSize = 14.ssp, fontFamily = fontFamily),

        bodyLarge = bodyLarge.copy(fontSize = 16.ssp, fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontSize = 14.ssp, fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontSize = 12.ssp, fontFamily = fontFamily),

        labelLarge = labelLarge.copy(fontSize = 14.ssp, fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontSize = 12.ssp, fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontSize = 11.ssp, fontFamily = fontFamily),
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ManRopeFontFamily() = FontFamily(
    Font(Res.font.manrope_light, weight = FontWeight.Light),
    Font(Res.font.manrope_regular, weight = FontWeight.Normal),
    Font(Res.font.manrope_medium, weight = FontWeight.Medium),
    Font(Res.font.manrope_semi_bold, weight = FontWeight.SemiBold),
    Font(Res.font.manrope_bold, weight = FontWeight.Bold),
    Font(Res.font.manrope_extra_bold, weight = FontWeight.ExtraBold),
    Font(Res.font.manrope_extra_light, weight = FontWeight.ExtraLight)
)