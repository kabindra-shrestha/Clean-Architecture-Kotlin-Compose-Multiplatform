package com.kabindra.clean.architecture.presentation.ui.screen.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.data.request.ApplyLeaveRequestDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyLeave
import com.kabindra.clean.architecture.domain.entity.LeaveDetails
import com.kabindra.clean.architecture.domain.entity.LeaveOption
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifier
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifierData
import com.kabindra.clean.architecture.domain.entity.LeaveTypes
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.DropdownField
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextError
import com.kabindra.clean.architecture.presentation.ui.component.filepicker.FilePicker
import com.kabindra.clean.architecture.presentation.ui.items.LeaveOptionItem
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.viewmodel.remote.LeaveViewModel
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.InputFieldIdType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError.handleInputFieldErrors
import com.kabindra.clean.architecture.utils.success.GlobalSuccessDialog
import dev.shivathapaa.nepalidatepickerkmp.NepaliDatePicker
import dev.shivathapaa.nepalidatepickerkmp.NepaliDatePickerDialog
import dev.shivathapaa.nepalidatepickerkmp.calendar_model.NepaliDatePickerDefaults
import dev.shivathapaa.nepalidatepickerkmp.rememberNepaliDatePickerState
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

private var documents: Set<PlatformFile> by mutableStateOf(emptySet())
private var isDocumentFieldValid by mutableStateOf(false)
private var documentFieldError by mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveRequestBottomSheet(
    leaveViewModel: LeaveViewModel = koinViewModel(),
    attendanceDate: String,
    onNavigateLogin: () -> Unit,
    onLeaveRequestApplied: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val leaveDetailsState by leaveViewModel.leaveDetailsState.collectAsState()
    val leaveRequestVerifierState by leaveViewModel.leaveRequestVerifierState.collectAsState()
    val applyLeaveRequestState by leaveViewModel.applyLeaveRequestState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var successType by remember { mutableStateOf<ResponseType>(ResponseType.None) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorMessages by remember { mutableStateOf<Map<String, List<String>>?>(null) }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }

    var isSelectedLeaveTypeValid by remember { mutableStateOf(false) }
    var selectedLeaveTypeError by remember { mutableStateOf("") }
    var leaveCountDays by remember { mutableStateOf("") }

    var showNepaliDatePickerDialog by remember { mutableStateOf(false) }
    val defaultNepaliDatePickerState = rememberNepaliDatePickerState()

    var leaveStartDateSelected by remember { mutableStateOf(attendanceDate) }
    var isLeaveStartDateValid by remember { mutableStateOf(false) }
    var leaveStartDateError by remember { mutableStateOf("") }

    var leaveEndDateSelected by remember { mutableStateOf(attendanceDate) }
    var isLeaveEndDateValid by remember { mutableStateOf(false) }
    var leaveEndDateError by remember { mutableStateOf("") }

    var selectedDateType by remember { mutableStateOf("") }

    var reasonField by remember { mutableStateOf("") }
    var isReasonFieldValid by remember { mutableStateOf(false) }
    var reasonFieldError by remember { mutableStateOf("") }

    var leaveRequestVerifierList by remember {
        mutableStateOf<List<LeaveRequestVerifierData>>(
            emptyList()
        )
    }
    var leaveRequestVerifierData by remember { mutableStateOf<LeaveRequestVerifierData?>(null) }
    var isLeaveRequestVerifierValid by remember { mutableStateOf(false) }
    var leaveRequestVerifierError by remember { mutableStateOf("") }

    var defaultLeaveOptionList by remember { mutableStateOf<List<LeaveOption>>(emptyList()) }
    var leaveOptionList by remember { mutableStateOf<List<LeaveOption>>(emptyList()) }

    var leaveTypeList by remember { mutableStateOf<List<LeaveTypes>>(emptyList()) }
    var leaveTypeData by remember { mutableStateOf<LeaveTypes?>(null) }

    var leaveOptionField by remember { mutableStateOf<LeaveOption?>(null) }
    var isLeaveOptionFieldValid by remember { mutableStateOf<Boolean?>(null) }
    var leaveOptionFieldError by remember { mutableStateOf("") }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
            leaveViewModel.resetStates()

            showLoading = false
            showSuccess = false
            showError = false

            isSelectedLeaveTypeValid = false
            selectedLeaveTypeError = ""

            isLeaveOptionFieldValid = false
            leaveOptionFieldError = ""

            isLeaveStartDateValid = false
            leaveStartDateError = ""

            isLeaveEndDateValid = false
            leaveEndDateError = ""

            isLeaveRequestVerifierValid = false
            leaveRequestVerifierError = ""

            isReasonFieldValid = false
            reasonFieldError = ""

            isDocumentFieldValid = false
            documentFieldError = ""
            documents = emptySet()
        }
    }

    LaunchedEffect(Unit) {
        leaveDetails(leaveViewModel)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (leaveTypeList.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = AppTheme.dimens.paddingSmall)
            ) {
                if (leaveTypeList.isNotEmpty()) {
                    DropdownField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.paddingSmall),
                        items = leaveTypeList,
                        itemContent = { leaveType: LeaveTypes -> leaveType.name!! },
                        value = if (leaveTypeData != null) {
                            leaveTypeData!!.name!!
                        } else {
                            ""
                        },
                        selectedItem = leaveTypeData,
                        onItemSelected = { selectedLeaveType ->
                            leaveTypeData = selectedLeaveType

                            if (!leaveTypeData?.name.isNullOrEmpty()) {
                                val leaveCountMessage = leaveTypeData?.leave_count_message
                                leaveCountDays = if (leaveCountMessage != null) {
                                    "$leaveCountMessage"
                                } else {
                                    ""
                                }
                                selectedLeaveTypeError = ""
                            } else {
                                leaveCountDays = ""
                                selectedLeaveTypeError = "The leave type field is required."
                            }
                            leaveOptionField = null
                            isLeaveOptionFieldValid = true
                            leaveOptionFieldError = ""
                            leaveOptionList = leaveTypeData!!.leave_options!!
                        },
                        label = "Select Leave Type",
                        leadingIcon = null,
                        isError = isSelectedLeaveTypeValid,
                        errorText = selectedLeaveTypeError
                    )
                    if (leaveCountDays.isNotEmpty()) {
                        TextComponent(
                            text = leaveCountDays,
                            modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingNormal),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InputField(
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(horizontal = AppTheme.dimens.paddingSmall),
                        value = leaveStartDateSelected,
                        onValueChange = {
                            leaveStartDateSelected = it
                            if (leaveStartDateSelected.isBlank()) {
                                isLeaveStartDateValid = false
                                leaveStartDateError = "The start date field is required."
                            } else {
                                isLeaveStartDateValid = true
                                leaveStartDateError = ""
                            }
                        },
                        label = "Start Date",
                        isError = isLeaveStartDateValid,
                        errorText = leaveStartDateError,
                        imeAction = ImeAction.Next,
                        trialingIcon = Icons.Default.CalendarMonth,
                        isEnabled = false,
                        onClickTrailingIcon = {
                            selectedDateType =
                                "start" // Mark that we are selecting the end date
                            showNepaliDatePickerDialog = true
                        }
                    )

                    InputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.4f)
                            .padding(horizontal = AppTheme.dimens.paddingSmall),
                        value = leaveEndDateSelected, //ready only value
                        onValueChange = {
                            leaveEndDateSelected = it
                            if (leaveEndDateSelected.isBlank()) {
                                isLeaveEndDateValid = false
                                leaveEndDateError = "The end date field is required."
                            } else {
                                isLeaveEndDateValid = true
                                leaveEndDateError = ""
                            }
                        },
                        label = "End Date",
                        isError = isLeaveEndDateValid,
                        errorText = leaveEndDateError,
                        imeAction = ImeAction.Next,
                        trialingIcon = Icons.Default.CalendarMonth,
                        isEnabled = false,
                        onClickTrailingIcon = {
                            selectedDateType = "end" // Mark that we are selecting the end date
                            showNepaliDatePickerDialog = true
                        }
                    )
                }

                if (defaultLeaveOptionList.isNotEmpty()) {
                    TextComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.paddingSmall),
                        text = "Leave Options",
                    )
                    BaseLazy(
                        modifier = Modifier
                            .wrapContentHeight(),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
                        arrangement = Arrangement.spacedBy(5.dp),
                        items = leaveOptionList.ifEmpty { defaultLeaveOptionList },
                        listType = LazyListType.LIST,
                        scrollDirection = LazyScrollDirection.HORIZONTAL,
                        itemContent = { index, item ->
                            LeaveOptionItem(
                                defaultLeaveOption = item,
                                isSelected = leaveOptionField?.id == item.id,
                                onClick = {
                                    leaveOptionField = it
                                    isLeaveOptionFieldValid = true
                                    leaveOptionFieldError = ""
                                },
                            )
                        },
                        isLoading = false,
                        loadingContent = { },
                        onLoadMore = { },
                        onScrollStateChanged = { },
                    )
                    if (isLeaveOptionFieldValid != null && !isLeaveOptionFieldValid!!) {
                        TextError(
                            text = leaveOptionFieldError,
                            modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingNormal)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingSmall),
                    value = reasonField,
                    onValueChange = {
                        reasonField = it
                        if (reasonField.isBlank()) {
                            isReasonFieldValid = false
                            reasonFieldError = "Reason must not be empty"
                        } else {
                            isReasonFieldValid = true
                            reasonFieldError = ""
                        }
                    },
                    label = "Reason for Taking Leave",
                    isError = isReasonFieldValid,
                    errorText = reasonFieldError,
                    imeAction = ImeAction.Next,
                    trialingIcon = null,
                    onClickTrailingIcon = {}
                )

                DropdownField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingSmall),
                    items = leaveRequestVerifierList,
                    itemContent = { leaveVerifier: LeaveRequestVerifierData -> leaveVerifier.name!! },
                    value = if (leaveRequestVerifierData != null) {
                        leaveRequestVerifierData!!.name!!
                    } else {
                        ""
                    },
                    selectedItem = leaveRequestVerifierData,
                    onItemSelected = {
                        leaveRequestVerifierData = it
                        if (leaveRequestVerifierData == null) {
                            isLeaveRequestVerifierValid = false
                            leaveRequestVerifierError = "Please select a service"
                        } else {
                            isLeaveRequestVerifierValid = true
                            leaveRequestVerifierError = ""
                        }
                    },
                    label = "Select Verifier",
                    leadingIcon = null,
                    isError = isLeaveRequestVerifierValid,
                    errorText = leaveRequestVerifierError

                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextComponent(
                        modifier = Modifier.padding(start = AppTheme.dimens.paddingSmall),
                        text = "Add Documents",
                        fontWeight = FontWeight.Bold
                    )
                    TextComponent(
                        modifier = Modifier,
                        text = "(pdf, doc, docx, jpeg, png, up to 2MB)",
                    )
                }

                Column(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.dimens.paddingSmall,
                    )
                ) {
                    FilePicker(
                        documents = documents,
                        onDocumentsChanged = { documents = it },
                        documentFieldError = documentFieldError
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ButtonTextAndIcon(
                            modifier = Modifier.weight(1f),
                            text = "Close",
                            iconVector = Icons.Default.Close,
                            iconContentDescription = "Close",
                            onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(AppTheme.dimens.paddingSmall))

                        ButtonTextAndIcon(
                            modifier = Modifier.weight(1f),
                            text = "Apply",
                            iconContentDescription = "Apply",
                            onClick = {
                                /*if (leaveStartDateSelected.isEmpty()) {
                                    isLeaveStartDateValid = false
                                    leaveStartDateError = "The start date field is required."
                                } else {
                                    isLeaveStartDateValid = true
                                    leaveStartDateError = ""
                                }
                                if (leaveEndDateSelected.isEmpty()) {
                                    isLeaveEndDateValid = false
                                    leaveEndDateError = "The end date field is required."
                                } else {
                                    isLeaveEndDateValid = true
                                    leaveEndDateError = ""
                                }
                                if (leaveTypeData == null) {
                                    isSelectedLeaveTypeValid = false
                                    selectedLeaveTypeError = " The leave type field is required."
                                } else {
                                    isSelectedLeaveTypeValid = true
                                    selectedLeaveTypeError = ""
                                }
                                if (reasonField.isBlank()) {
                                    isReasonFieldValid = false
                                    reasonFieldError = "The reason field is required. "
                                } else {
                                    isReasonFieldValid = true
                                    reasonFieldError = ""
                                }
                                if (leaveRequestVerifierData == null) {
                                    isLeaveRequestVerifierValid = false
                                    leaveRequestVerifierError = "Please select verifier"
                                } else {
                                    isLeaveRequestVerifierValid = true
                                    leaveRequestVerifierError = ""
                                }

                                if (leaveOptionField == null) {
                                    isLeaveOptionFieldValid = false
                                    leaveOptionFieldError = "The leave option field is required."
                                } else {
                                    isLeaveOptionFieldValid = true
                                    leaveOptionFieldError = ""
                                }

                                if (isLeaveStartDateValid && isLeaveEndDateValid && isSelectedLeaveTypeValid && isReasonFieldValid && isLeaveRequestVerifierValid && isLeaveOptionFieldValid!!) {
                                    applyLeaveRequest(
                                        leaveViewModel,
                                        leave_type_id = leaveTypeData!!.id.toString(),
                                        leave_option_id = leaveOptionField!!.id.toString(),
                                        nep_start_date = leaveStartDateSelected,
                                        nep_end_date = leaveEndDateSelected,
                                        verifier_id = leaveRequestVerifierData!!.id.toString(),
                                        documents = documents,
                                        remarks = reasonField
                                    )
                                }*/

                                applyLeaveRequest(
                                    leaveViewModel,
                                    leave_type_id = leaveTypeData?.id?.toString() ?: "",
                                    leave_option_id = leaveOptionField?.id?.toString() ?: "",
                                    nep_start_date = leaveStartDateSelected,
                                    nep_end_date = leaveEndDateSelected,
                                    verifier_id = leaveRequestVerifierData?.id?.toString()
                                        ?: "",
                                    documents = documents.toList(),
                                    remarks = reasonField
                                )
                            }
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

    LaunchedEffect(leaveDetailsState) {
        when (leaveDetailsState) {
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

                var leaveDetails =
                    (leaveDetailsState as Result.Success<LeaveDetails>).data

                leaveTypeList = leaveDetails.response!!.leave_types!!
                defaultLeaveOptionList = leaveDetails.response!!.default_leave_options!!

                if (leaveTypeList.isEmpty()) {
                    showError = true
                    errorStatusCode = -1
                    errorTitle = ""
                    errorMessage = "No Leave Type Found"
                    errorType = ResponseType.None

                    scope.launch {
                        delay(2000)
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                } else {
                    leaveRequestVerifiers(leaveViewModel)

                }
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (leaveDetailsState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (leaveDetailsState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(leaveRequestVerifierState) {
        when (leaveRequestVerifierState) {
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

                val leaveRequestVerifier: LeaveRequestVerifier =
                    (leaveRequestVerifierState as Result.Success<LeaveRequestVerifier>).data

                leaveRequestVerifierList = leaveRequestVerifier.response?.verifiers!!
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (leaveRequestVerifierState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (leaveRequestVerifierState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(applyLeaveRequestState) {
        when (applyLeaveRequestState) {
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

                val applyLeaveRequest: ApplyLeave =
                    (applyLeaveRequestState as Result.Success<ApplyLeave>).data

                successMessage = applyLeaveRequest.message
                successType = ResponseType.ApplyLeaveRequest
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                errorStatusCode = (applyLeaveRequestState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (applyLeaveRequestState as Result.Error).error.message
                errorMessages = (applyLeaveRequestState as Result.Error).error.errors
                errorType = ResponseType.None

                if (!errorMessages.isNullOrEmpty()) {
                    showError = false

                    handleInputFieldErrors(errorMessages) { field, error ->
                        when (field) {
                            InputFieldIdType.LeaveRequestLeaveType -> {
                                isSelectedLeaveTypeValid = error == null
                                selectedLeaveTypeError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestLeaveOption -> {
                                isLeaveOptionFieldValid = error == null
                                leaveOptionFieldError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestStartDate -> {
                                isLeaveStartDateValid = error == null
                                leaveStartDateError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestEndDate -> {
                                isLeaveEndDateValid = error == null
                                leaveEndDateError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestVerifier -> {
                                isLeaveRequestVerifierValid = error == null
                                leaveRequestVerifierError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestRemarks -> {
                                isReasonFieldValid = error == null
                                reasonFieldError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestDocuments -> {
                                isDocumentFieldValid = error == null
                                documentFieldError = error ?: ""
                            }

                            else -> {}
                        }
                    }
                } else {
                    showError = true
                }
            }
        }
    }

    if (showSuccess) {
        GlobalSuccessDialog(
            isVisible = showSuccess,
            isAction = true,
            message = successMessage,
            onDismiss = {
                /*scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onDismissRequest()
                    }
                }*/

                onLeaveRequestApplied()
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
            onNavigateLogin = { onNavigateLogin() })
    }

    if (showNepaliDatePickerDialog) {
        NepaliDatePickerDialog(
            confirmButton = {
                NepaliDatePickerDefaults.DialogButton(
                    text = "OK",
                    onButtonClick = {
                        val selectedDate =
                            defaultNepaliDatePickerState.selectedDate?.let { date ->
                                "${date.year}-${
                                    date.month.toString().padStart(2, '0')
                                }-${date.dayOfMonth.toString().padStart(2, '0')}"
                            } ?: ""
                        //try matrai natra same duita haldine
                        if (selectedDateType == "start") {
                            leaveStartDateSelected = selectedDate
                            isLeaveStartDateValid = true
                            leaveStartDateError = ""
                        } else if (selectedDateType == "end") {
                            leaveEndDateSelected = selectedDate
                            isLeaveEndDateValid = true
                            leaveEndDateError = ""
                        }

                        showNepaliDatePickerDialog = false
                    }
                )
            },
            dismissButton = {
                NepaliDatePickerDefaults.DialogButton(
                    text = "Cancel",
                    onButtonClick = { showNepaliDatePickerDialog = false }
                )
            },
            onDismissRequest = { showNepaliDatePickerDialog = false }
        ) {
            NepaliDatePicker(state = defaultNepaliDatePickerState)

        }
    }
}

private fun leaveRequestVerifiers(attendanceViewModel: LeaveViewModel) {
    attendanceViewModel.getLeaveRequestVerifiers()
}

private fun applyLeaveRequest(
    attendanceViewModel: LeaveViewModel,
    leave_type_id: String,
    leave_option_id: String,
    nep_start_date: String,
    nep_end_date: String,
    verifier_id: String,
    documents: List<PlatformFile>,
    remarks: String
) {

    attendanceViewModel.getApplyLeaveRequest(
        ApplyLeaveRequestDataRequest(
            leave_type_id,
            leave_option_id,
            nep_start_date,
            nep_end_date,
            verifier_id,
            documents,
            remarks
        )
    )
}

private fun leaveDetails(attendanceViewModel: LeaveViewModel) {
    attendanceViewModel.getLeaveDetails()
}

