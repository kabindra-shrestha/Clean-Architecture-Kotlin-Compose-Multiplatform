package com.kabindra.clean.architecture

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.kabindra.clean.architecture.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()

    ComposeViewport {
        App()
    }
}