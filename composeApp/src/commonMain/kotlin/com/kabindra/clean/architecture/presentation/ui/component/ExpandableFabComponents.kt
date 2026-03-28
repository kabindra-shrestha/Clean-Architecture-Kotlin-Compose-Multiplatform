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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.kabindra.clean.architecture.presentation.ui.theme.overlay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> ExpandableFabComponent(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    expanded: Boolean = false,
    useExpressive: Boolean = true,
    items: List<T>,
    mainFabIcon: ImageVector = Icons.Default.Add,
    itemTitle: (T) -> String,
    itemIcon: (T) -> ImageVector,
    onItemClick: (T) -> Unit
) {
    if (useExpressive) {
        ExpressiveExpandableFabComponent(
            modifier = modifier,
            expanded = expanded,
            items = items,
            mainFabIcon = mainFabIcon,
            itemTitle = itemTitle,
            itemIcon = itemIcon,
            onItemClick = onItemClick
        )
    } else {
        LegacyExpandableFabComponent(
            modifier = modifier,
            shape = shape,
            expanded = expanded,
            items = items,
            mainFabIcon = mainFabIcon,
            itemTitle = itemTitle,
            itemIcon = itemIcon,
            onItemClick = onItemClick
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun <T> ExpressiveExpandableFabComponent(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    items: List<T>,
    mainFabIcon: ImageVector = Icons.Default.Add,
    itemTitle: (T) -> String,
    itemIcon: (T) -> ImageVector,
    onItemClick: (T) -> Unit
) {
    val scope = rememberCoroutineScope()
    var expand by remember { mutableStateOf(expanded) }

    // BackHandler(enabled = expand) { expand = false }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (expand) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlay.copy(alpha = 0.8f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { expand = false }
                    )
            )
        }

        FloatingActionButtonMenu(
            modifier = Modifier.padding(bottom = 28.sdp),
            expanded = expand,
            button = {
                ToggleFloatingActionButton(
                    checked = expand,
                    onCheckedChange = { expand = it },
                ) {
                    val iconVector by remember(mainFabIcon) {
                        derivedStateOf {
                            if (checkedProgress > 0.5f) Icons.Default.Close else mainFabIcon
                        }
                    }
                    ImageHandlerVector(
                        modifier = Modifier.animateIcon(checkedProgress = { checkedProgress }),
                        image = iconVector,
                        contentDescription = "Expand actions"
                    )
                }
            },
        ) {
            items.forEach { item ->
                FloatingActionButtonMenuItem(
                    onClick = {
                        expand = false
                        scope.launch {
                            delay(160)
                            onItemClick(item)
                        }
                    },
                    text = { Text(text = itemTitle(item)) },
                    icon = {
                        ImageHandlerVector(
                            modifier = Modifier.size(13.sdp),
                            image = itemIcon(item),
                            contentDescription = itemTitle(item)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun <T> LegacyExpandableFabComponent(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
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
                    modifier
                        .fillMaxSize()
                        .background(overlay.copy(alpha = 0.8f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
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
                modifier = Modifier.padding(5.sdp),
                verticalArrangement = Arrangement.spacedBy(5.sdp)
            ) {
                items.forEach { item ->
                    LegacyExpandableFabRow(
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
            modifier = Modifier.padding(bottom = 28.sdp),
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
private fun <T> LegacyExpandableFabRow(
    item: T,
    itemTitle: (T) -> String,
    itemIcon: (T) -> ImageVector,
    onFabClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.width(120.sdp),
        text = { Text(text = itemTitle(item)) },
        icon = {
            ImageHandlerVector(
                modifier = Modifier
                    .size(19.sdp)
                    .padding(1.sdp),
                image = itemIcon(item),
                contentDescription = itemTitle(item)
            )
        },
        onClick = onFabClick,
    )
}
