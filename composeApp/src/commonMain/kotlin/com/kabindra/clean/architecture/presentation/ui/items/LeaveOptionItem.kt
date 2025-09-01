package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.LeaveOption
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.theme.leaveOptionSelected
import com.kabindra.clean.architecture.presentation.ui.theme.leaveOptionUnselected
import com.kabindra.clean.architecture.presentation.ui.theme.textSelected
import com.kabindra.clean.architecture.presentation.ui.theme.textUnselected

@Composable
fun LeaveOptionItem(
    defaultLeaveOption: LeaveOption,
    isSelected: Boolean,
    onClick: (LeaveOption) -> Unit,
) {
    CardBorderInside(
        containerColor = if (isSelected) leaveOptionSelected else leaveOptionUnselected,
        modifier = Modifier
            .size(width = 110.dp, height = 50.dp)
            .clickable { onClick(defaultLeaveOption) }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            TextComponent(
                text = defaultLeaveOption.name!!,
                color = if (isSelected) textSelected else textUnselected
            )
        }

    }
}