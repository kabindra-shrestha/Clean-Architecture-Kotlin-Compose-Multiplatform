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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.Notification
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextTitleMedium
import com.kabindra.clean.architecture.presentation.ui.component.TextTitleSmall
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme

@Composable
fun ItemNotification(
    notification: Notification,
    onNotificationItemClick: () -> Unit,
    onClick: (notificationId: String) -> Unit
) {
    CardBorderInside(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .clickable { onNotificationItemClick() },
        sides = listOf(),
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                ItemNotificationHeader(notification, onClick = { onClick(it) })
            }
        }
    )
}

enum class NotificationType(val slug: String) {
    NotificationHeader("notifications-header"),
}

inline fun <reified T : Enum<T>> getTicketContentType(slug: String): NotificationType {
    return enumValues<T>().find { (it as NotificationType).slug == slug } as NotificationType
}

@Composable
fun NotificationIconTextComponent(
    icon: ImageVector = Icons.Default.PushPin,
    text: String,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    iconSize: Int = 15,
    spacing: Dp = AppTheme.dimens.paddingExtraSmall,
    isClickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = if (isClickable) {
            modifier
                .clickable { onClick() }
                .padding(
                    horizontal = AppTheme.dimens.paddingNormal,
                    vertical = AppTheme.dimens.paddingSmall
                )
        } else {
            modifier.padding(
                horizontal = AppTheme.dimens.paddingNormal,
                vertical = AppTheme.dimens.paddingSmall
            )
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageHandlerVector(
            modifier = Modifier.size(iconSize.dp),
            image = icon,
            tint = tint
        )
        Spacer(modifier = Modifier.width(spacing))
        TextComponent(text = text)
    }
}

@Composable
fun ItemNotificationHeader(
    notification: Notification,
    onClick: (notificationId: String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageHandlerVector(
            modifier = Modifier.size(40.dp)
                .padding(2.dp),
            image = Icons.Default.BookmarkAdded,
            contentDescription = "Notification"
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            TextTitleMedium(
                text = notification.title!!.takeIf { it.isNotEmpty() }
                    ?: "N/A",
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextTitleMedium(
                text = notification.body!!.takeIf { it.isNotEmpty() }
                    ?: "N/A",
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageHandlerVector(
                    modifier = Modifier.size(14.dp),
                    image = Icons.Default.AccessTime,
                    contentDescription = "Notification"
                )
                TextTitleSmall(
                    text = notification.time.takeIf { it!!.isNotEmpty() }
                        ?: "N/A",
                )

            }
            if (notification.isUnread!!) {
                Spacer(modifier = Modifier.height(4.dp))
                TextTitleMedium(
                    text = "Mark as read",
                    modifier = Modifier
                        .clickable { onClick(notification.id.toString()) }
                )
            }
        }
    }
}
