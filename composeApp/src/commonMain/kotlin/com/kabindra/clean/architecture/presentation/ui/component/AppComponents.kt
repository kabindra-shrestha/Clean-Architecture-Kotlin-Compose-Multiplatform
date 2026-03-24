package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import composemultiplatformcleanarchitecture.composeapp.generated.resources.Res
import composemultiplatformcleanarchitecture.composeapp.generated.resources.splash_icon
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun AppIcon(
    modifier: Modifier = Modifier
        .width(250.sdp)
        .height(250.sdp)
) {
    ImageHandlerRes(
        modifier = modifier,
        image = Res.drawable.splash_icon,
        contentDescription = "App Icon",
    )
}

@Composable
fun AppIconFilled(
    modifier: Modifier = Modifier
        .width(163.sdp)
        .height(63.sdp)
) {
    ImageHandlerRes(
        modifier = modifier,
        image = Res.drawable.splash_icon,
        contentDescription = "App Icon",
    )
}

@Composable
fun AppBrandIcon(
    modifier: Modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight()
) {
    ImageHandlerRes(
        modifier = modifier,
        image = Res.drawable.splash_icon,
        contentDescription = "Brand Icon",
    )
}