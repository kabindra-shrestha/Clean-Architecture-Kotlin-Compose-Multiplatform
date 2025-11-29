@file:OptIn(ExperimentalResourceApi::class)

package com.kabindra.clean.architecture.utils.confirmation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GlobalDialogComponent(
    isVisible: Boolean,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    if (isVisible) {
        Dialog(onDismissRequest = onDismissRequest) {
            content()
            /*Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(createDimensions().paddingSmall),
                shape = RoundedCornerShape(16.sdp),
            ) {
                content()
            }*/
        }
    }
}

