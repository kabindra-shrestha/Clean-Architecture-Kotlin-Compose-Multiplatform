@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import com.kabindra.clean.architecture.presentation.ui.theme.createDimensions
import network.chaintech.sdpcomposemultiplatform.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetComponent(
    modifier: Modifier = Modifier,
    title: String,
    text: String = "",
    icon: ImageVector = Icons.Default.Close,
    isVisible: Boolean,
    isExpanded: Boolean = false,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (isVisible) {
        val sheetState = if (isExpanded) {
            rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
        } else {
            rememberModalBottomSheetState()
        }
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = createDimensions().paddingSmall)
                    ) {
                        TextComponent(
                            text = title,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold,
                            type = TextType.Title,
                            size = TextSize.Large
                        )
                        IconButton(onClick = { onDismiss() }) {
                            ImageHandlerVector(
                                modifier = Modifier
                                    .size(20.sdp)
                                    .aspectRatio(1f),
                                image = icon,
                                contentDescription = text
                            )
                        }
                    }
                    content()
                }
            }
        )
    }
}



