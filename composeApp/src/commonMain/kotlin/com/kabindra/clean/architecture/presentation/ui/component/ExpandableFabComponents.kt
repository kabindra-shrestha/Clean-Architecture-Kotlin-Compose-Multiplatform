package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.theme.overlay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> ExpandableFabComponent(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CircleShape,
    expanded: Boolean = false,
    items: List<T>,
    mainFabIcon: ImageVector = Icons.Default.Add,
    itemTitle: (T) -> String,
    itemIcon: (T) -> ImageVector,
    onItemClick: (T) -> Unit
) {
    val scope = rememberCoroutineScope()

    var expand by remember { mutableStateOf(expanded) }

    Column(
        modifier = modifier
            .then(
                if (expand) {
                    modifier.fillMaxSize()
                        .background(overlay.copy(alpha = 0.8f))
                        .clickable(
                            interactionSource = MutableInteractionSource(), indication = null,
                            onClick = { expand = false })
                } else {
                    modifier.fillMaxSize()
                }
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = expand,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEach { item ->
                    ExpandableFabRow(
                        item = item,
                        itemTitle = itemTitle,
                        itemIcon = itemIcon,
                        onFabClick = {
                            expand = false

                            scope.launch {
                                delay(200)
                                onItemClick(item)
                            }
                        }
                    )
                }
            }
        }

        val rotation by animateFloatAsState(
            targetValue = if (expand) 45f else 0f,
            label = "fabRotation"
        )

        FloatingActionButton(
            modifier = Modifier.padding(bottom = 47.dp),
            shape = shape,
            onClick = { expand = !expand }
        ) {
            ImageHandlerVector(
                modifier = Modifier.rotate(rotation),
                image = mainFabIcon,
                contentDescription = "Expand actions"
            )
        }
    }
}

@Composable
private fun <T> ExpandableFabRow(
    item: T,
    itemTitle: (T) -> String,
    itemIcon: (T) -> ImageVector,
    onFabClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.width(200.dp),
        text = { Text(text = itemTitle(item)) },
        icon = {
            ImageHandlerVector(
                modifier = Modifier.size(32.dp).padding(2.dp),
                image = itemIcon(item),
                contentDescription = itemTitle(item)
            )
        },
        onClick = onFabClick,
    )
}