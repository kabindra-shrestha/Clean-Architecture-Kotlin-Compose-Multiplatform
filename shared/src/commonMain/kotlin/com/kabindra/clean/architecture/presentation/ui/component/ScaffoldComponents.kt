package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.LoginRoute
import com.kabindra.clean.architecture.utils.enums.MenuType
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun Lifecycle.observeAsSate(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsSate.addObserver(observer)
        onDispose {
            this@observeAsSate.removeObserver(observer)
        }
    }
    return state
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TopAppBarComponent(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    canNavigateBack: Boolean,
    useExpressive: Boolean = true,
    onBackNavigate: () -> Unit
) {
    val navigationIcon: @Composable () -> Unit = {
        if (canNavigateBack) {
            ButtonBack(
                useExpressiveShapes = useExpressive
            ) { onBackNavigate() }
        }
    }

    if (useExpressive && subtitle.isNotBlank()) {
        TopAppBar(
            title = { TextComponent(text = title) },
            subtitle = { TextComponent(text = subtitle) },
            modifier = modifier,
            navigationIcon = navigationIcon,
        )
    } else {
        TopAppBar(
            title = { TextComponent(text = title) },
            modifier = modifier,
            navigationIcon = navigationIcon
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TopAppBarWithBackComponent(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    useExpressive: Boolean = true,
    onBackNavigate: () -> Unit
) {
    if (useExpressive && subtitle.isNotBlank()) {
        TopAppBar(
            title = {
                TextComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    size = TextSize.Large,
                    textAlign = TextAlign.Start
                )
            },
            subtitle = {
                TextComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = subtitle,
                    size = TextSize.Small,
                    textAlign = TextAlign.Start
                )
            },
            modifier = modifier,
            navigationIcon = { ButtonBack(useExpressiveShapes = useExpressive) { onBackNavigate() } },
        )
    } else {
        TopAppBar(
            title = {
                TextComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    size = TextSize.Large,
                    textAlign = TextAlign.Start
                )
            },
            modifier = modifier,
            navigationIcon = { ButtonBack(useExpressiveShapes = useExpressive) { onBackNavigate() } },
        )
    }
}

data class FloatingToolbarAction(
    val id: String,
    val label: String,
    val icon: ImageVector,
)

enum class FloatingToolbarVariant {
    HORIZONTAL,
    HORIZONTAL_WITH_FAB,
    VERTICAL,
    VERTICAL_WITH_FAB,
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FloatingToolbarComponent(
    modifier: Modifier = Modifier,
    items: List<FloatingToolbarAction>,
    variant: FloatingToolbarVariant = FloatingToolbarVariant.HORIZONTAL,
    expanded: Boolean = true,
    showLabelBelowIcon: Boolean = false,
    useVibrantColors: Boolean = true,
    fabIcon: ImageVector? = null,
    onItemClick: (FloatingToolbarAction) -> Unit = {},
    onFabClick: () -> Unit = {},
) {
    if (items.isEmpty()) return

    val colors = if (useVibrantColors) {
        FloatingToolbarDefaults.vibrantFloatingToolbarColors()
    } else {
        FloatingToolbarDefaults.standardFloatingToolbarColors()
    }

    val resolvedFabIcon = fabIcon ?: items.first().icon
    val floatingActionButton: @Composable () -> Unit = {
        if (useVibrantColors) {
            FloatingToolbarDefaults.VibrantFloatingActionButton(onClick = onFabClick) {
                Icon(
                    imageVector = resolvedFabIcon,
                    contentDescription = "Floating toolbar primary action"
                )
            }
        } else {
            FloatingToolbarDefaults.StandardFloatingActionButton(onClick = onFabClick) {
                Icon(
                    imageVector = resolvedFabIcon,
                    contentDescription = "Floating toolbar primary action"
                )
            }
        }
    }

    when (variant) {
        FloatingToolbarVariant.HORIZONTAL -> {
            HorizontalFloatingToolbar(
                expanded = expanded,
                modifier = modifier,
                colors = colors,
                content = {
                    ToolbarHorizontalActions(
                        items = items,
                        showLabelBelowIcon = showLabelBelowIcon,
                        onItemClick = onItemClick,
                    )
                },
            )
        }

        FloatingToolbarVariant.HORIZONTAL_WITH_FAB -> {
            HorizontalFloatingToolbar(
                expanded = expanded,
                floatingActionButton = floatingActionButton,
                modifier = modifier,
                colors = colors,
                content = {
                    ToolbarHorizontalActions(
                        items = items,
                        showLabelBelowIcon = showLabelBelowIcon,
                        onItemClick = onItemClick,
                    )
                },
            )
        }

        FloatingToolbarVariant.VERTICAL -> {
            VerticalFloatingToolbar(
                expanded = expanded,
                modifier = modifier,
                colors = colors,
                content = {
                    ToolbarVerticalActions(
                        items = items,
                        showLabelBelowIcon = showLabelBelowIcon,
                        onItemClick = onItemClick,
                    )
                },
            )
        }

        FloatingToolbarVariant.VERTICAL_WITH_FAB -> {
            VerticalFloatingToolbar(
                expanded = expanded,
                floatingActionButton = floatingActionButton,
                modifier = modifier,
                colors = colors,
                content = {
                    ToolbarVerticalActions(
                        items = items,
                        showLabelBelowIcon = showLabelBelowIcon,
                        onItemClick = onItemClick,
                    )
                },
            )
        }
    }
}

@Composable
private fun RowScope.ToolbarHorizontalActions(
    items: List<FloatingToolbarAction>,
    showLabelBelowIcon: Boolean,
    onItemClick: (FloatingToolbarAction) -> Unit,
) {
    items.forEach { action ->
        FloatingToolbarActionItemContent(
            item = action,
            showLabelBelowIcon = showLabelBelowIcon,
            onItemClick = onItemClick,
        )
    }
}

@Composable
private fun ColumnScope.ToolbarVerticalActions(
    items: List<FloatingToolbarAction>,
    showLabelBelowIcon: Boolean,
    onItemClick: (FloatingToolbarAction) -> Unit,
) {
    items.forEach { action ->
        FloatingToolbarActionItemContent(
            item = action,
            showLabelBelowIcon = showLabelBelowIcon,
            onItemClick = onItemClick,
        )
    }
}

@Composable
private fun FloatingToolbarActionItemContent(
    item: FloatingToolbarAction,
    showLabelBelowIcon: Boolean,
    onItemClick: (FloatingToolbarAction) -> Unit,
) {
    if (showLabelBelowIcon) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onItemClick(item) }
                .padding(horizontal = 5.sdp, vertical = 2.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.sdp),
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
            )
            TextComponent(
                text = item.label,
                type = TextType.Label,
                size = TextSize.Small,
                maxLines = 1,
            )
        }
    } else {
        IconButton(onClick = { onItemClick(item) }) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
            )
        }
    }
}

@Composable
fun BottomNavigationBarComponent(
    modifier: Modifier = Modifier,
    selectedRoute: String = LoginRoute.Route,
    onClick: (selectedSlug: String) -> Unit
) {
    NavigationBar(modifier = modifier) {
        MenuType.entries
            .filter { it.isBottomNavigation }
            .forEach { label ->
                NavigationBarItem(
                    icon = {
                        label.icon?.let {
                            ImageHandlerVector(
                                modifier = Modifier
                                    .size(19.sdp)
                                    .padding(1.sdp),
                                image = it,
                                contentDescription = label.title
                            )
                        }
                    },
                    label = {
                        TextComponent(
                            text = label.title,
                            textAlign = TextAlign.Center,
                            maxLines = 3,
                            size = TextSize.Small
                        )
                    },
                    selected = if (label.route != null) {
                        selectedRoute.contains(label.route.toString())
                    } else {
                        false
                    },
                    onClick = {
                        onClick(label.slug)
                    }
                )
            }
    }
}
