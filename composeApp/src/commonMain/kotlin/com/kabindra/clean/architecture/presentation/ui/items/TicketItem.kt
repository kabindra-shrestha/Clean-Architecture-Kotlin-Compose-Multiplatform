package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.TicketData
import com.kabindra.clean.architecture.presentation.ui.component.ButtonAction
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.theme.divider
import com.kabindra.clean.architecture.presentation.ui.theme.imageBorder
import com.kabindra.clean.architecture.presentation.ui.theme.onBackgroundLight
import com.kabindra.clean.architecture.utils.enums.TicketActionType
import com.kabindra.clean.architecture.utils.enums.TicketDetailsTitle
import com.kabindra.clean.architecture.utils.enums.TicketWorkflowType


@Composable
fun ItemTicketRequest(
    item: TicketData,
    onClickEdit: (ticketId: Int, ticketWorkflow: String) -> Unit,
    onClickDetails: (ticketId: Int, ticketWorkflow: String) -> Unit,
    onClick: (ticketId: Int, ticketWorkflow: String, actionTitle: String, actionSlug: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        CardBorderInside(
            modifier = Modifier.fillMaxWidth(),
            sides = listOf()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ItemTicketHeader(
                    item,
                    onClickEdit = { ticketId, ticketWorkflow ->
                        onClickEdit(
                            ticketId,
                            ticketWorkflow
                        )
                    },
                    onClickDetails = { ticketId, ticketWorkflow ->
                        onClickDetails(
                            ticketId,
                            ticketWorkflow
                        )
                    },
                    onClick = { ticketId, ticketWorkflow, actionTitle, actionSlug ->
                        onClick(
                            ticketId,
                            ticketWorkflow,
                            actionTitle, actionSlug
                        )
                    }
                )


                /*  if (item.workflow == TicketWorkflowType.TimeRequestApproval.slug) {
                      ItemTicketTimeRequest(item)
                  }
                  if (item.workflow == TicketWorkflowType.LeaveApproval.slug) {
                      ItemTicketLeaveRequest(item)
                  }*/
                /* ItemTicketFooter(
                     item,
                     onClick = { ticketId, ticketWorkflow, actionTitle, actionSlug ->
                         onClick(
                             ticketId,
                             ticketWorkflow,
                             actionTitle, actionSlug
                         )
                     }
                 )*/
            }
        }
    }

}

@Composable
fun ItemTicketHeader(
    item: TicketData,
    onClickEdit: (ticketId: Int, ticketWorkflow: String) -> Unit,
    onClickDetails: (ticketId: Int, ticketWorkflow: String) -> Unit,
    onClick: (ticketId: Int, ticketWorkflow: String, actionTitle: String, actionSlug: String) -> Unit

) {
    var isExpanded by remember { mutableStateOf(false) }

    val ticketDetailTitleEnum = TicketDetailsTitle.entries.find {
        item.detail?.title!!.contains(it.detailTitle, ignoreCase = true)
    } ?: TicketDetailsTitle.LeaveRequest

    Column(
        modifier = Modifier.fillMaxWidth().animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ImageHandlerURL(
                    modifier = Modifier.size(32.dp),
                    image = item.employee?.profile_picture ?: "",
                    contentDescription = "Profile Icon",
                    contentScale = ContentScale.Crop,
                    placeholder = Icons.Default.Person,
                    circular = true,
                    backgroundColor = imageBorder
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.Center) {
                TextComponent(
                    text = "${item.employee?.name ?: ""} (${item.employee?.employee_code ?: ""})",
                    fontWeight = FontWeight.Bold
                )
                item.employee?.takeIf { it.department!!.isNotEmpty() || it.branch!!.isNotEmpty() }
                    .let {
                        TextComponent(
                            text = "${it?.department ?: ""}, ${it?.branch ?: ""}",
                            size = TextSize.Medium
                        )
                    }
            }
            Spacer(modifier = Modifier.weight(1f))
            ImageHandlerVector(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { isExpanded = !isExpanded },
                image = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = onBackgroundLight
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .border(1.dp, ticketDetailTitleEnum.getColor(), RoundedCornerShape(8.dp))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                if (!item.detail?.title.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = ticketDetailTitleEnum.getColor(),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        TextComponent(
                            text = item.detail?.title ?: "",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                if (item.workflow == TicketWorkflowType.TimeRequestApproval.slug) {
                    TextComponent(
                        text = buildAnnotatedString {
                            append("Request Date: ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(item.detail?.applied_on.takeIf { it!!.isNotEmpty() }
                                    ?: "N/A")
                            }
                        }, maxLines = 2
                    )
                } else {
                    TextComponent(
                        text = "${item.detail?.start_date ?: ""} - ${item.detail?.end_date ?: ""}",
                        fontWeight = FontWeight.Bold, maxLines = 2
                    )
                }
            }

        }

        /* Box(
             modifier = Modifier
                 .fillMaxWidth()
                 .background(
                     color = attendanceStatusEnum.getColor(),
                     shape = RoundedCornerShape(8.dp)
                 )
                 .padding(12.dp)
         ) {
             TextComponent(text = attendance.status.takeIf { it!!.isNotEmpty() }
                 ?: "N/A", fontWeight = FontWeight.Bold)
         }*/
        AnimatedVisibility(visible = isExpanded) {
            if (item.workflow == TicketWorkflowType.TimeRequestApproval.slug) {
                ItemTicketTimeRequest(item)
            }
            if (item.workflow == TicketWorkflowType.LeaveApproval.slug) {
                ItemTicketLeaveRequest(item)
            }

        }
        ItemTicketFooter(
            item,
            onClick = { ticketId, ticketWorkflow, actionTitle, actionSlug ->
                onClick(
                    ticketId,
                    ticketWorkflow,
                    actionTitle, actionSlug
                )
            },
            onClickEdit = { ticketId, ticketWorkflow ->
                onClickEdit(ticketId, ticketWorkflow)
            },
            onClickDetails = { ticketId, ticketWorkflow ->
                onClickDetails(ticketId, ticketWorkflow)
            }
        )

        /* Row(
             modifier = Modifier.fillMaxWidth()
                 .padding(horizontal = 4.dp, vertical = 4.dp),
             verticalAlignment = Alignment.CenterVertically
         ) {

             //need to change here
             Column(
                 modifier = Modifier.weight(0.7f),
                 verticalArrangement = Arrangement.Center
             ) {
                 if (!item.detail?.title.isNullOrEmpty()) {
                     TextTitleSmall(text = item.detail?.title ?: "")
                 }
                 if (!item.detail?.subtitle.isNullOrEmpty()) {
                     Spacer(modifier = Modifier.height(2.dp))
                     TextTitleSmall(text = item.detail?.subtitle ?: "")
                 }
             }
             Row(
                 modifier = Modifier.weight(0.3f),
                 horizontalArrangement = Arrangement.End
             ) {
                 if (item.access?.edit!!) {
                     ButtonEdit { onClickEdit(item.detail?.ticket_id!!, item.workflow!!) }
                 }
                 ButtonDetails { onClickDetails(item.detail?.ticket_id!!, item.workflow!!) }
             }
         }
         HorizontalDivider(color = divider)*/
    }
}

@Composable
fun ItemTicketFooter(
    item: TicketData,
    onClickEdit: (ticketId: Int, ticketWorkflow: String) -> Unit,
    onClickDetails: (ticketId: Int, ticketWorkflow: String) -> Unit,
    onClick: (ticketId: Int, ticketWorkflow: String, actionTitle: String, actionSlug: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    ImageHandlerURL(
                        modifier = Modifier.size(26.dp),
                        image = item.current_owner?.profile_picture ?: "",
                        contentDescription = "Profile Icon",
                        contentScale = ContentScale.Crop,
                        placeholder = Icons.Default.Person,
                        circular = true,
                        backgroundColor = imageBorder
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = item.current_owner?.name ?: ""
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (item.access?.edit!!) {
                    ButtonAction { onClickEdit(item.detail?.ticket_id!!, item.workflow!!) }
                }
                ButtonAction(tint = Color(0xFF22C55E), iconVector = Icons.Default.Visibility) {
                    onClickDetails(
                        item.detail?.ticket_id!!,
                        item.workflow!!
                    )
                }

                TicketActionType.entries.forEachIndexed { index, label ->
                    val access = item.access

                    val isActionAllowed = when (label.slug) {
                        TicketActionType.Approve.slug -> access?.approve!!
                        TicketActionType.Cancel.slug -> access?.cancel!!
                        else -> false
                    }

                    if (isActionAllowed) {
                        ButtonAction(
                            tint = Color(0xFFEF4444),
                            iconVector = label.icon!!,
                            iconContentDescription = label.title,
                        ) {
                            onClick(
                                item.detail?.ticket_id!!,
                                item.workflow!!,
                                label.title,
                                label.slug
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTicketTimeRequest(item: TicketData) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        /* Row(modifier = Modifier.fillMaxWidth()) {
             TextTitleMedium(
                 text = "Applied On:",
                 fontWeight = FontWeight.Bold
             )
             Spacer(modifier = Modifier.width(8.dp))
             TextTitleMedium(
                 text = item.detail?.applied_on ?: "",
                 maxLines = 2
             )
         }
         Spacer(modifier = Modifier.width(8.dp))
         Row(modifier = Modifier.fillMaxWidth()) {
             TextTitleMedium(
                 text = "Date:",
                 fontWeight = FontWeight.Bold
             )
             Spacer(modifier = Modifier.width(8.dp))
             TextTitleMedium(
                 text = item.detail?.date ?: "",
                 maxLines = 2
             )
         }
         Spacer(modifier = Modifier.width(16.dp))*/
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Device In:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextComponent(
                text = item.detail?.device_in ?: "",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Requested In:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextComponent(
                text = item.detail?.requested_in ?: "",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "In Note:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextComponent(
                text = item.detail?.in_note ?: "",
                fontWeight = FontWeight.Bold,
                maxLines = 2,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(color = divider)

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Device Out:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextComponent(
                text = item.detail?.device_out ?: "",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Requested Out:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextComponent(
                text = item.detail?.requested_out ?: "",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Out Note:",
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextComponent(
                text = item.detail?.out_note ?: "",
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(color = divider)

        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun ItemTicketLeaveRequest(item: TicketData) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        /*TextTitleMedium(
            text = "${item.detail?.start_date ?: ""} - ${item.detail?.end_date ?: ""}"
        )*/
        Spacer(modifier = Modifier.width(8.dp))
        item.detail?.num_days?.let {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Number of Days:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = "$it day"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Applied On:",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextComponent(
                text = item.detail?.applied_on ?: "",
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = "Reason:",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextComponent(
                text = item.detail?.remarks ?: "",
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        HorizontalDivider(color = divider)

        Spacer(modifier = Modifier.height(4.dp))
    }
}