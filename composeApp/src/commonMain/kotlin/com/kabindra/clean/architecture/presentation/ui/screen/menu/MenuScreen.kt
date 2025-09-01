package com.kabindra.clean.architecture.presentation.ui.screen.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent

@Composable
fun MenuScreen() {
    Box {
        TextComponent(
            modifier = Modifier.align(Alignment.Center),
            text = "Menu Screen"
        )
    }
}