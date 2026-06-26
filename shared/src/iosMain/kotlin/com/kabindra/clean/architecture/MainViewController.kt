package com.kabindra.clean.architecture

import androidx.compose.ui.window.ComposeUIViewController
import com.kabindra.clean.architecture.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }