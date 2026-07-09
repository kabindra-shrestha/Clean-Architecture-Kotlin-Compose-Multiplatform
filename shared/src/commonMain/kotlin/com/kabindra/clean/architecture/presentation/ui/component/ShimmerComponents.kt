package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.valentinilk.shimmer.shimmer
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
    height: Dp = 100.sdp,
    width: Modifier = Modifier.fillMaxWidth(),
    cornerRadius: Dp = 8.sdp
) {
    Box(
        modifier = modifier
            .then(width)
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .shimmer()
            .background(Color.LightGray.copy(alpha = 0.3f))
    )
}
