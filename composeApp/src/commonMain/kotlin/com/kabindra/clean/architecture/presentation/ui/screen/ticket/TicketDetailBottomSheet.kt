package com.kabindra.clean.architecture.presentation.ui.screen.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.data.request.TicketChangeStateRequest
import com.kabindra.clean.architecture.data.request.TicketDetailsRequest
import com.kabindra.clean.architecture.domain.entity.Owner
import com.kabindra.clean.architecture.domain.entity.TicketChangeState
import com.kabindra.clean.architecture.domain.entity.TicketDetails
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.items.ItemTicketDetailAction
import com.kabindra.clean.architecture.presentation.ui.items.ItemTicketDetailDocuments
import com.kabindra.clean.architecture.presentation.ui.items.ItemTicketDetailHeader
import com.kabindra.clean.architecture.presentation.ui.items.ItemTicketDetailHistory
import com.kabindra.clean.architecture.presentation.ui.items.ItemTicketLeaveRequestDetail
import com.kabindra.clean.architecture.presentation.ui.items.ItemTicketTimeRequestDetail
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.viewmodel.remote.TicketViewModel
import com.kabindra.clean.architecture.utils.confirmation.GlobalConfirmationDialog
import com.kabindra.clean.architecture.utils.constants.ConfirmationType
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.TicketActionType
import com.kabindra.clean.architecture.utils.enums.TicketRemarkType
import com.kabindra.clean.architecture.utils.enums.TicketWorkflowType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import org.koin.compose.viewmodel.koinViewModel

private var actionTitleClicked: String = ""
private var actionSlugClicked: String = ""
private var actionOwnerClicked: List<Owner> = listOf()
private var actionRemarksClicked: String = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDetailBottomSheet(
    ticketViewModel: TicketViewModel = koinViewModel(),
    ticketId: Int,
    onActionDone: (needUpdate: Boolean) -> Unit,
    onClose: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val ticketDetailsState by ticketViewModel.ticketDetailsState.collectAsState()
    val ticketChangeStateState by ticketViewModel.ticketChangeStateStateDetail.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var successType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var showConfirmation by remember { mutableStateOf(false) }
    var confirmationMessage by remember { mutableStateOf("") }
    var confirmationType by remember { mutableStateOf<ConfirmationType>(ConfirmationType.None) }

    var showTicketCommentsBottomSheet by remember { mutableStateOf(false) }

    var ticketsDetailsData by remember { mutableStateOf<TicketDetails?>(null) }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
            ticketViewModel.resetStates()

            showLoading = false
            showSuccess = false
            showError = false
        }
    }

    LaunchedEffect(Unit) {
        ticketDetail(ticketViewModel, ticketId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (ticketsDetailsData != null) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = AppTheme.dimens.paddingNormal, vertical = 2.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(), indication = null,
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
            ) {
                ItemTicketDetailAction(
                    ticketDetailsInfo = ticketsDetailsData!!.response!!,
                    onClick = { actionTitle, actionSlug ->
                        when (actionSlug) {
                            TicketActionType.Approve.slug -> {
                                actionTitleClicked = actionTitle
                                actionSlugClicked = actionSlug
                                actionOwnerClicked =
                                    ticketsDetailsData!!.response!!.actions?.approve?.owners!!
                                actionRemarksClicked =
                                    ticketsDetailsData!!.response!!.actions?.approve?.remarks!!
                            }

                            TicketActionType.Cancel.slug -> {
                                actionTitleClicked = actionTitle
                                actionSlugClicked = actionSlug
                                actionOwnerClicked =
                                    ticketsDetailsData!!.response!!.actions?.cancel?.owners!!
                                actionRemarksClicked =
                                    ticketsDetailsData!!.response!!.actions?.cancel?.remarks!!
                            }

                            TicketActionType.ChangeOwner.slug -> {
                                actionTitleClicked = actionTitle
                                actionSlugClicked = actionSlug
                                actionOwnerClicked =
                                    ticketsDetailsData!!.response!!.actions?.change_owner?.owners!!
                                actionRemarksClicked =
                                    ticketsDetailsData!!.response!!.actions?.change_owner?.remarks!!
                            }

                            TicketActionType.Reject.slug -> {
                                actionTitleClicked = actionTitle
                                actionSlugClicked = actionSlug
                                actionOwnerClicked =
                                    ticketsDetailsData!!.response!!.actions?.reject?.owners!!
                                actionRemarksClicked =
                                    ticketsDetailsData!!.response!!.actions?.reject?.remarks!!
                            }

                            TicketActionType.Revert.slug -> {
                                actionTitleClicked = actionTitle
                                actionSlugClicked = actionSlug
                                actionOwnerClicked =
                                    ticketsDetailsData!!.response!!.actions?.revert?.owners!!
                                actionRemarksClicked =
                                    ticketsDetailsData!!.response!!.actions?.revert?.remarks!!
                            }

                            TicketActionType.Verify.slug -> {
                                actionTitleClicked = actionTitle
                                actionSlugClicked = actionSlug
                                actionOwnerClicked =
                                    ticketsDetailsData!!.response!!.actions?.verify?.owners!!
                                actionRemarksClicked =
                                    ticketsDetailsData!!.response!!.actions?.verify?.remarks!!
                            }

                            else -> {
                                actionTitleClicked = ""
                                actionSlugClicked = ""
                                actionOwnerClicked = listOf()
                                actionRemarksClicked = ""
                            }
                        }

                        if (actionOwnerClicked.isNotEmpty() ||
                            actionRemarksClicked == TicketRemarkType.Required.type ||
                            actionRemarksClicked == TicketRemarkType.Optional.type
                        ) {
                            showTicketCommentsBottomSheet = true
                        } else {
                            showConfirmation = true
                            confirmationMessage =
                                "Are you sure to ${actionSlugClicked.replace("_", "")} ticket?"
                            confirmationType = ConfirmationType.TicketChangeState
                        }
                    }
                )
                CardBorderInside(
                    modifier = Modifier.fillMaxWidth(),
                    sides = listOf()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        ItemTicketDetailHeader(
                            ticketDetailsInfo = ticketsDetailsData!!.response!!
                        )
                        if (ticketsDetailsData!!.response!!.workflow == TicketWorkflowType.TimeRequestApproval.slug) {
                            /*TextComponent(
                                text = ticketsDetailsData?.response?.detail?.title!!,
                                fontWeight = FontWeight.Bold
                            )*/

                            Spacer(modifier = Modifier.width(8.dp))
                            ItemTicketTimeRequestDetail(
                                ticketDetailsInfo = ticketsDetailsData!!.response!!
                            )
                        }
                        if (ticketsDetailsData!!.response!!.workflow == TicketWorkflowType.LeaveApproval.slug) {
                            /* TextComponent(
                                 text = "${ ticketsDetailsData?.response?.detail?.title!! } (${ticketsDetailsData?.response?.detail?.subtitle})",
                                 fontWeight = FontWeight.Bold
                             )*/

                            Spacer(modifier = Modifier.width(8.dp))
                            ItemTicketLeaveRequestDetail(
                                ticketDetailsInfo = ticketsDetailsData!!.response!!
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        ItemTicketDetailDocuments(
                            ticketDetailsInfo = ticketsDetailsData!!.response!!
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        ItemTicketDetailHistory(
                            ticketDetailsInfo = ticketsDetailsData!!.response!!
                        )
                    }
                }
            }
        }

        if (showLoading) {
            LoadingIndicator(
                modifier = Modifier.align(Alignment.Center),
                isCircular = true
            )
        }
    }

    LaunchedEffect(ticketDetailsState) {
        when (ticketDetailsState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = true
                showSuccess = false
                showError = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = false
                showError = false

                val ticketDetails = (ticketDetailsState as Result.Success<TicketDetails>).data

                ticketsDetailsData = ticketDetails

                if (successType == ResponseType.TicketChangeState) {
                    onActionDone(true)
                }
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (ticketDetailsState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (ticketDetailsState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(ticketChangeStateState) {
        when (ticketChangeStateState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = true
                showSuccess = false
                showError = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = true
                showError = false

                val ticketChangeState =
                    (ticketChangeStateState as Result.Success<TicketChangeState>).data

                successMessage = ticketChangeState.message
                successType = ResponseType.TicketChangeState
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (ticketChangeStateState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (ticketChangeStateState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    if (showSuccess) {
        GlobalSuccessDialog(
            isVisible = showSuccess,
            isAction = true,
            message = successMessage,
            onDismiss = {
                if (successType == ResponseType.TicketChangeState) {
                    ticketDetail(ticketViewModel, ticketId)
                }
            }
        )
    }

    if (showError) {
        GlobalErrorDialog(
            isVisible = showError,
            isAction = true,
            statusCode = errorStatusCode,
            title = errorTitle,
            message = errorMessage,
            onDismiss = {
                showError = false
                errorStatusCode = -1
                errorTitle = ""
                errorMessage = ""
            },
            onNavigateLogin = { })
    }

    if (showConfirmation) {
        GlobalConfirmationDialog(
            isVisible = showConfirmation,
            isAction = true,
            message = confirmationMessage,
            onCancel = {
                showConfirmation = false

                actionTitleClicked = ""
                actionSlugClicked = ""
                actionOwnerClicked = listOf()
                actionRemarksClicked = ""
            },
            onContinue = {
                showConfirmation = false

                if (confirmationType == ConfirmationType.TicketChangeState) {
                    ticketChangeState(
                        ticketViewModel,
                        ticketId.toString(),
                        actionSlugClicked,
                        "",
                        ""
                    )
                }
            })
    }

    if (showTicketCommentsBottomSheet) {
        ModalBottomSheetComponent(
            modifier = Modifier.padding(
                AppTheme.dimens.paddingSmall
            ),
            title = actionTitleClicked,
            isVisible = showTicketCommentsBottomSheet,
            icon = Icons.Default.Close,  // Pass the icon you want to use for closing
            onDismiss = {
                showTicketCommentsBottomSheet = false

                actionTitleClicked = ""
                actionSlugClicked = ""
                actionOwnerClicked = listOf()
                actionRemarksClicked = ""
            },
            content = {
                TicketCommentsBottomSheet(
                    owners = actionOwnerClicked,
                    remarks = actionRemarksClicked,
                    onSend = { ownersField, commentsField ->
                        showTicketCommentsBottomSheet = false

                        ticketChangeState(
                            ticketViewModel,
                            ticketId.toString(),
                            actionSlugClicked,
                            commentsField!!,
                            ownersField?.id?.toString() ?: ""
                        )
                    },
                    onClose = {
                        showTicketCommentsBottomSheet = false

                        actionTitleClicked = ""
                        actionSlugClicked = ""
                        actionOwnerClicked = listOf()
                        actionRemarksClicked = ""
                    }
                )
            }
        )
    }
}

private fun ticketDetail(
    ticketViewModel: TicketViewModel,
    ticketId: Int
) {
    ticketViewModel.getTicketDetails(
        TicketDetailsRequest(
            ticketId.toString()
        )
    )
}

private fun ticketChangeState(
    ticketViewModel: TicketViewModel,
    ticketId: String,
    state: String,
    comment: String,
    nextOwnerId: String
) {
    ticketViewModel.getTicketChangeStateDetail(
        TicketChangeStateRequest(
            ticketId,
            state,
            comment,
            nextOwnerId
        )
    )
}