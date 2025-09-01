package com.kabindra.clean.architecture.presentation.ui.component.timeline

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import com.kabindra.clean.architecture.presentation.ui.component.timeline.defaults.LineParametersDefaults
import com.kabindra.clean.architecture.presentation.ui.component.timeline.models.LineParameters
import com.kabindra.clean.architecture.presentation.ui.component.timeline.models.TimelineNodePosition

fun mapToTimelineNodePosition(index: Int, collectionSize: Int) = when (index) {
    0 -> TimelineNodePosition.FIRST
    collectionSize - 1 -> TimelineNodePosition.LAST
    else -> TimelineNodePosition.MIDDLE
}

@Composable
fun getLineBrush(lastIndex: Boolean = true): LineParameters? {
    return if (lastIndex) {
        LineParametersDefaults.linearGradient(
            startColor = LocalContentColor.current,
            endColor = LocalContentColor.current
        )
    } else {
        null
    }
}