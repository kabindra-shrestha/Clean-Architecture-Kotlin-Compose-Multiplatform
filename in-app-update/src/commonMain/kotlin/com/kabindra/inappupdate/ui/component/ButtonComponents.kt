package com.kabindra.inappupdate.ui.component

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kabindra.inappupdate.ui.theme.createDimensions

@Composable
fun ButtonNormal(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .requiredWidth(createDimensions().minButtonWidth),
        enabled = enabled,
        onClick = onClick
    ) {
        TextButtonAction(text = text)
    }
}