package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.theme.cardBorder
import com.kabindra.clean.architecture.presentation.ui.theme.transparent

enum class BorderSide {
    LEFT, TOP, RIGHT, BOTTOM
}

@Composable
fun CardBorderInside(
    modifier: Modifier = Modifier,
    borderColor: Color = cardBorder,
    borderWidth: Dp = 2.dp,
    containerColor: Color = transparent,
    sides: List<BorderSide> = listOf(
        BorderSide.LEFT,
        BorderSide.TOP,
        BorderSide.RIGHT,
        BorderSide.BOTTOM
    ),
    content: @Composable () -> Unit
) {
    val borderWidthPx = with(LocalDensity.current) { borderWidth.toPx() }
    val halfStroke = borderWidthPx / 2

    OutlinedCard(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.drawBorder(borderColor, borderWidthPx, halfStroke, sides)
                .background(containerColor)
                .padding(borderWidth)
        ) {
            content()
        }
    }
}

private fun Modifier.drawBorder(
    color: Color,
    borderWidth: Float,
    halfStroke: Float,
    sides: List<BorderSide>
): Modifier = this.then(
    drawBehind {
        sides.forEach { side ->
            when (side) {
                BorderSide.LEFT -> drawLeftBorder(color, borderWidth, halfStroke)
                BorderSide.TOP -> drawTopBorder(color, borderWidth, halfStroke)
                BorderSide.RIGHT -> drawRightBorder(color, borderWidth, halfStroke)
                BorderSide.BOTTOM -> drawBottomBorder(color, borderWidth, halfStroke)
            }
        }
    }
)

private fun DrawScope.drawLeftBorder(color: Color, borderWidth: Float, halfStroke: Float) {
    drawLine(
        color = color,
        start = Offset(x = halfStroke, y = 0f),
        end = Offset(x = halfStroke, y = size.height),
        strokeWidth = borderWidth
    )
}

private fun DrawScope.drawTopBorder(color: Color, borderWidth: Float, halfStroke: Float) {
    drawLine(
        color = color,
        start = Offset(x = 0f, y = halfStroke),
        end = Offset(x = size.width, y = halfStroke),
        strokeWidth = borderWidth
    )
}

private fun DrawScope.drawRightBorder(color: Color, borderWidth: Float, halfStroke: Float) {
    drawLine(
        color = color,
        start = Offset(x = size.width - halfStroke, y = 0f),
        end = Offset(x = size.width - halfStroke, y = size.height),
        strokeWidth = borderWidth
    )
}

private fun DrawScope.drawBottomBorder(color: Color, borderWidth: Float, halfStroke: Float) {
    drawLine(
        color = color,
        start = Offset(x = 0f, y = size.height - halfStroke),
        end = Offset(x = size.width, y = size.height - halfStroke),
        strokeWidth = borderWidth
    )
}