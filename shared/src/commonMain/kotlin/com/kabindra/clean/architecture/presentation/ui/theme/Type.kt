package com.kabindra.clean.architecture.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import composemultiplatformcleanarchitecture.shared.generated.resources.Res
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_bold
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_extra_bold
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_extra_light
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_light
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_medium
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_regular
import composemultiplatformcleanarchitecture.shared.generated.resources.manrope_semi_bold
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@Composable
fun AppTypography() = Typography().run {
    val fontFamily = ManRopeFontFamily()

    copy(
        // 57 * 0.6 = 34, 45 * 0.6 = 27, 36 * 0.6 = 22
        displayLarge = displayLarge.copy(fontSize = 34.ssp, fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontSize = 27.ssp, fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontSize = 22.ssp, fontFamily = fontFamily),

        // 32 * 0.6 = 19, 28 * 0.6 = 17, 24 * 0.6 = 14
        headlineLarge = headlineLarge.copy(fontSize = 19.ssp, fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontSize = 17.ssp, fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontSize = 14.ssp, fontFamily = fontFamily),

        // 22 * 0.6 = 13, 16 * 0.6 = 10, 14 * 0.6 = 8
        titleLarge = titleLarge.copy(fontSize = 13.ssp, fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontSize = 10.ssp, fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontSize = 8.ssp, fontFamily = fontFamily),

        // 16 * 0.6 = 10, 14 * 0.6 = 8, 12 * 0.6 = 7
        bodyLarge = bodyLarge.copy(fontSize = 10.ssp, fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontSize = 8.ssp, fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontSize = 7.ssp, fontFamily = fontFamily),

        // 14 * 0.6 = 8, 12 * 0.6 = 7, 11 * 0.6 = 7
        labelLarge = labelLarge.copy(fontSize = 8.ssp, fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontSize = 7.ssp, fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontSize = 7.ssp, fontFamily = fontFamily),
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