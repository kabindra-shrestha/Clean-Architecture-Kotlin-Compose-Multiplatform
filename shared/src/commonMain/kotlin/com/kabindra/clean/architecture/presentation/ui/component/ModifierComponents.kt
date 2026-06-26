package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }

fun Modifier.parallaxLayoutModifier(scrollState: LazyListState, rate: Int) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val offset = scrollState.firstVisibleItemScrollOffset / rate
        layout(placeable.width, placeable.height) {
            placeable.place(0, offset)
        }
    }