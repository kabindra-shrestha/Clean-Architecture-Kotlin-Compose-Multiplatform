package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.DashboardContent
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.TextType
import com.kabindra.clean.architecture.utils.enums.MenuType
import com.kabindra.clean.architecture.utils.enums.getMenuType

@Composable
fun ItemHomeTicket(
    content: DashboardContent,
    onClick: () -> Unit
) {
    CardBorderInside(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                onClick()
            },
        borderWidth = 2.dp,
        sides = listOf(),

        ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(100.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,

                ) {
                TextComponent(
                    modifier = Modifier.weight(0.7f),
                    text = content.workflow_name!!,
                    maxLines = 2,
                )
                ImageHandlerVector(
                    modifier = Modifier.size(24.dp).weight(0.3f),
                    image = /*content.icon!!*/ Icons.Default.CalendarMonth,
                    contentDescription = content.workflow_name!!
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextComponent(
                    text = content.count.toString(),
                    type = TextType.Headline,
                    size = TextSize.Large,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun ItemHomeMenu(
    content: DashboardContent,
    onClick: (String) -> Unit
) {
    CardBorderInside(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                onClick(content.type!!)
            },
        sides = listOf(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ImageHandlerVector(
                modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                image = /*data.icon!!*/content.type?.let { getMenuType<MenuType>(it).icon },
                contentDescription = content.name!!
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextComponent(
                text = content.name
            )
        }
    }
}