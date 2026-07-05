package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun CheckboxCustom(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    useExpressiveStyle: Boolean = true,
    label: String = ""
) {
    val strokeWidthPx =
        with(LocalDensity.current) { 1.sdp.toPx() }
    val checkmarkStroke = remember(strokeWidthPx) {
        Stroke(width = strokeWidthPx, cap = StrokeCap.Round, join = StrokeJoin.Round)
    }
    val outlineStroke = remember(strokeWidthPx) {
        Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (useExpressiveStyle) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onCheckedChange(it) },
                checkmarkStroke = checkmarkStroke,
                outlineStroke = outlineStroke
            )
        } else {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
        TextComponent(
            modifier = Modifier.weight(1f),
            text = label
        )
    }
}
