package com.kabindra.architecture

import androidx.compose.ui.window.ComposeUIViewController
import com.kabindra.architecture.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    app()
}