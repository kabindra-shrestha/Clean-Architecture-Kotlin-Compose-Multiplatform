package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.theme.drawerBackgroundSelected
import com.kabindra.clean.architecture.presentation.ui.theme.drawerBackgroundUnselected
import com.kabindra.clean.architecture.presentation.ui.theme.drawerTextSelected
import com.kabindra.clean.architecture.presentation.ui.theme.drawerTextUnselected
import com.kabindra.clean.architecture.utils.enums.MenuType

@Composable
fun DrawerItem(
    label: MenuType,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    val selectedRadius = 65.dp
    val selectedBackground =
        if (selected) drawerBackgroundSelected else drawerBackgroundUnselected
    val selectedItemColor =
        if (selected) drawerTextSelected else drawerTextUnselected

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = selectedRadius,
                    bottomStart = selectedRadius
                )
            )
            .background(
                color = selectedBackground,
                shape = RoundedCornerShape(
                    topStart = selectedRadius,
                    bottomStart = selectedRadius
                )
            )
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ImageHandlerVector(
            modifier = Modifier.size(32.dp).padding(2.dp),
            image = label.icon,
            contentDescription = "Menu Icons",
            tint = selectedItemColor,
            circular = true,
            backgroundColor = selectedBackground
        )

        TextComponent(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = label.title,
            fontWeight = FontWeight.Bold,
            color = selectedItemColor
        )
    }
}
