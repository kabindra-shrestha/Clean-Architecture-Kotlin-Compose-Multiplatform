package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.Route
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    modifier: Modifier = Modifier,
    title: String = "",
    canNavigateBack: Boolean,
    onBackNavigate: () -> Unit
) {
    TopAppBar(
        title = { TextSmall(text = title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                ButtonBack {
                    onBackNavigate()
                }
            }
        }
    )
}

@Composable
fun TopAppBarWithBackComponent(
    modifier: Modifier = Modifier.height(54.sdp),
    title: String = "",
    onBackNavigate: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonBack {
            onBackNavigate()
        }
        TextComponent(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            size = TextSize.Large,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun BottomNavigationBarComponent(
    modifier: Modifier = Modifier,
    selectedRoute: String = Route.LoginMainRoute::class.qualifiedName!!,
    onClick: (selectedSlug: String) -> Unit
) {
    NavigationBar {
        MenuType.entries
            .filter { it.isBottomNavigation }
            .forEachIndexed { index, label ->
                NavigationBarItem(
                    icon = {
                        label.icon?.let {
                            ImageHandlerVector(
                                modifier = Modifier.size(32.sdp).padding(1.sdp),
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
                        selectedRoute.contains(label.route::class.qualifiedName!!)
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