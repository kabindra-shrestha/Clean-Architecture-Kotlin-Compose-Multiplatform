package com.kabindra.clean.architecture.presentation.ui.component.timeline.defaults

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.timeline.models.CircleParameters
import com.kabindra.clean.architecture.presentation.ui.component.timeline.models.StrokeParameters

object CircleParametersDefaults {

    private val defaultCircleRadius = 12.dp

    fun circleParameters(
        radius: Dp = defaultCircleRadius,
        backgroundColor: Color = Color.Cyan,
        stroke: StrokeParameters? = null,
        icon: ImageVector? = null
    ) = CircleParameters(
        radius,
        backgroundColor,
        stroke,
        icon
    )
}