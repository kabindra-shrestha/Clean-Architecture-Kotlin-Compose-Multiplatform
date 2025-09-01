package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.StateHistory
import com.kabindra.clean.architecture.domain.entity.TicketDetailsInfo
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTopIconAndText
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.TextTitleMedium
import com.kabindra.clean.architecture.presentation.ui.component.timeline.TimelineNode
import com.kabindra.clean.architecture.presentation.ui.component.timeline.defaults.CircleParametersDefaults
import com.kabindra.clean.architecture.presentation.ui.component.timeline.getLineBrush
import com.kabindra.clean.architecture.presentation.ui.component.timeline.mapToTimelineNodePosition
import com.kabindra.clean.architecture.presentation.ui.theme.ticketHistoryStatus
import com.kabindra.clean.architecture.presentation.ui.theme.ticketTimelineBorder
import com.kabindra.clean.architecture.utils.enums.TicketActionType
import com.kabindra.clean.architecture.utils.enums.TicketStateType
import com.kabindra.clean.architecture.utils.enums.TicketWorkflowType
import com.kabindra.clean.architecture.utils.enums.getTicketStateType
import com.kabindra.clean.architecture.utils.enums.getTicketWorkflowType
import com.kabindra.clean.architecture.utils.openFile

@Composable
fun ItemTicketDetailHeader(
    ticketDetailsInfo: TicketDetailsInfo
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
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
                    image = ticketDetailsInfo.employee?.profile_picture ?: "",
                    contentDescription = "Profile Icon",
                    contentScale = ContentScale.Crop,
                    placeholder = Icons.Default.Person,
                    circular = true
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                TextComponent(
                    text = "${ticketDetailsInfo.employee?.name ?: ""} (${ticketDetailsInfo.employee?.employee_code ?: ""})",
                    fontWeight = FontWeight.Bold
                )
                TextComponent(
                    text = "${ticketDetailsInfo.employee?.department ?: ""}, ${ticketDetailsInfo.employee?.branch ?: ""}",
                    size = TextSize.Medium
                )
            }
        }
    }
}

@Composable
fun ItemTicketDetailDocuments(ticketDetailsInfo: TicketDetailsInfo) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
        ) {
            TextComponent(
                text = "Documents",
                fontWeight = FontWeight.Bold
            )
            if (ticketDetailsInfo.documents?.isEmpty()!!) {
                Spacer(modifier = Modifier.height(8.dp))
                TextComponent(
                    text = "No Documents Found"
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                BaseLazy(
                    modifier = Modifier
                        .wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    arrangement = Arrangement.spacedBy(4.dp),
                    items = ticketDetailsInfo.documents,
                    listType = LazyListType.LIST,
                    scrollDirection = LazyScrollDirection.HORIZONTAL,
                    itemContent = { index, item ->
                        ImageHandlerURL(
                            modifier = Modifier.size(50.dp),
                            image = item.path!!,
                            contentDescription = "Documents Icon",
                            contentScale = ContentScale.Crop,
                            placeholder = Icons.AutoMirrored.Filled.Note,
                            isClickable = true,
                            onClick = {
                                openFile(
                                    item.path, item.extension!!,
                                    onError = {
                                        println(it)
                                    }
                                )
                            }
                        )
                    },
                    isLoading = false,
                    loadingContent = { },
                    onLoadMore = { },
                    onScrollStateChanged = { },
                )
            }
        }
    }
}

@Composable
fun ItemTicketDetailHistory(ticketDetailsInfo: TicketDetailsInfo) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
        ) {
            TextComponent(
                text = "History",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ticketDetailsInfo.state_history?.forEachIndexed { index, item ->
                    TimelineNode(
                        position = mapToTimelineNodePosition(
                            index,
                            ticketDetailsInfo.state_history.size
                        ),
                        circleParameters = CircleParametersDefaults.circleParameters(
                            backgroundColor = ticketTimelineBorder,
                            icon = getTicketStateType<TicketStateType>(item.state!!).icon
                        ),
                        lineParameters = getLineBrush(lastIndex = index != ticketDetailsInfo.state_history.lastIndex)
                    ) { modifier ->
                        HistoryData(modifier, item)
                    }
                }
            }
        }

    }
}

@Composable
fun HistoryData(modifier: Modifier, stateHistory: StateHistory) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextComponent(
                text = buildAnnotatedString {
                    append("${stateHistory.actor?.name ?: ""} ")

                    stateHistory.state.let {
                        if (it.equals(TicketStateType.Current.title)) {
                            append("hasn't performed any action yet.")
                        } else {
                            withStyle(
                                style = SpanStyle(
                                    background = ticketHistoryStatus
                                )
                            ) {
                                append(" $it ")
                            }
                            append(" the ${stateHistory.title}.")
                        }
                    }
                },
                maxLines = 2
            )
        }
        stateHistory.time?.takeIf { it.isNotEmpty() }?.let {
            Spacer(modifier = Modifier.height(4.dp))
            TextComponent(text = it)
        }
        stateHistory.documents?.takeIf { it.isNotEmpty() }?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    TextComponent(
                        text = "Documents",
                        fontWeight = FontWeight.Bold
                    )
                    if (stateHistory.documents.isEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextTitleMedium(
                            text = "No Documents Found"
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        BaseLazy(
                            modifier = Modifier
                                .wrapContentHeight(),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            arrangement = Arrangement.spacedBy(4.dp),
                            items = stateHistory.documents,
                            listType = LazyListType.LIST,
                            scrollDirection = LazyScrollDirection.HORIZONTAL,
                            itemContent = { index, item ->
                                ImageHandlerURL(
                                    modifier = Modifier.size(50.dp),
                                    image = item.document!!,
                                    contentDescription = "Documents Icon",
                                    contentScale = ContentScale.Crop,
                                    placeholder = Icons.AutoMirrored.Filled.Note,
                                    isClickable = true,
                                    onClick = {
                                        openFile(
                                            item.document, item.extension!!,
                                            onError = {
                                                println(it)
                                            }
                                        )
                                    }
                                )
                            },
                            isLoading = false,
                            loadingContent = { },
                            onLoadMore = { },
                            onScrollStateChanged = { },
                        )
                    }
                }
            }
        }
        stateHistory.remarks?.takeIf { it.isNotEmpty() }?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Remarks:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = it,
                    maxLines = 2
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemTicketDetailAction(
    ticketDetailsInfo: TicketDetailsInfo,
    onClick: (actionTitle: String, actionSlug: String) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 4
    ) {
        TicketActionType.entries.forEachIndexed { index, label ->
            val access = ticketDetailsInfo.access

            val isActionAllowed = when (label.slug) {
                TicketActionType.Approve.slug -> access?.approve!!
                TicketActionType.Cancel.slug -> access?.cancel!!
                TicketActionType.ChangeOwner.slug -> access?.change_owner!!
                TicketActionType.Reject.slug -> access?.reject!!
                TicketActionType.Revert.slug -> access?.revert!!
                TicketActionType.Verify.slug -> access?.verify!!
                else -> false
            }

            if (isActionAllowed) {
                ButtonTopIconAndText(
                    modifier = Modifier.size(90.dp),
                    modifierIcon = Modifier.size(30.dp),
                    iconVector = label.icon!!,
                    iconContentDescription = label.title,
                    text = label.title
                ) { onClick(label.title, label.slug) }
            }
        }
    }
}

@Composable
fun ItemTicketTimeRequestDetail(ticketDetailsInfo: TicketDetailsInfo) {

    val ticketDetailApprovalTitleEnum =
        getTicketWorkflowType<TicketWorkflowType>(ticketDetailsInfo.workflow!!)

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 2.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .border(
                    1.dp,
                    ticketDetailApprovalTitleEnum.color,
                    RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = ticketDetailApprovalTitleEnum.color,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {

                    TextComponent(
                        text = ticketDetailsInfo.detail?.title!!,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Date:",
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.date ?: "",
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Time Request Count In 30 Days:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = "${ticketDetailsInfo.detail?.time_request_count ?: 0}"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Device In:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.device_in ?: "",
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Requested In:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.requested_in ?: "",
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "In Note:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.in_note ?: "",
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Device Out:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.device_out ?: "",
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Requested Out:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.requested_out ?: "",
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Out Note:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.out_note ?: "",
                    maxLines = 2
                )
            }
        }
    }

}

@Composable
fun ItemTicketLeaveRequestDetail(ticketDetailsInfo: TicketDetailsInfo) {

    val ticketDetailApprovalTitleEnum =
        getTicketWorkflowType<TicketWorkflowType>(ticketDetailsInfo.workflow!!)

    Column(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .border(
                        1.dp,
                        ticketDetailApprovalTitleEnum.color,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = ticketDetailApprovalTitleEnum.color,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {

                        TextComponent(
                            text = "${ticketDetailsInfo.detail?.title!!} (${ticketDetailsInfo.detail.subtitle!!})",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Start Date:"
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.start_date ?: "",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "End Date:"
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.end_date ?: "",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Number of Days:"
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.num_days.let { "$it day(s)" },
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Requested On:"
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.applied_on ?: "",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextComponent(
                    text = "Remarks:"
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextComponent(
                    text = ticketDetailsInfo.detail?.remarks ?: "",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                )
            }
            ticketDetailsInfo.detail?.leave_count?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextComponent(
                        text = "Leave Count In 30 Days:"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    TextComponent(
                        text = "$it",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        ticketDetailsInfo.detail?.leave_balance?.let {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                TextComponent(
                    text = "Leave Balance",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    ticketDetailsInfo.detail.leave_balance.forEachIndexed { index, leaveBalance ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextComponent(
                                text = "${leaveBalance.name}:"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            TextComponent(
                                text = "${leaveBalance.leave_taken ?: 0}/${leaveBalance.assigned_leave ?: 0} ${leaveBalance.pending_leave.let { "(Pending: $it)" }},",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}