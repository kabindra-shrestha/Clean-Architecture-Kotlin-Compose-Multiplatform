package com.kabindra.architecture

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.kabindra.architecture.presentation.ui.screen.NewsScreen
import com.kabindra.architecture.presentation.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
@Preview
fun app() {
    KoinContext {
        AppTheme {
            /*val snackBarHostState = remember { SnackbarHostState() }
            val localCoroutineScope = rememberCoroutineScope()*/

            val snackBarHostState: SnackbarHostState = koinInject()

            Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
                NewsScreen()
            }
        }
    }
}