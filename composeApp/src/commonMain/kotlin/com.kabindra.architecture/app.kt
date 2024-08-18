package com.kabindra.architecture

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import com.kabindra.architecture.presentation.ui.screen.NewsScreen
import com.kabindra.architecture.presentation.ui.theme.AppTheme

@Composable
@Preview
fun app() {
    KoinContext {
        AppTheme {
            NewsScreen()
        }
    }
}