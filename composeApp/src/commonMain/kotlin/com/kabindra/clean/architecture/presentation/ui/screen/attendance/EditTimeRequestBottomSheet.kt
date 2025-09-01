package com.kabindra.clean.architecture.presentation.ui.screen.attendance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabindra.clean.architecture.data.request.TicketDocumentRemoveRequest
import com.kabindra.clean.architecture.data.request.TimeRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyTime
import com.kabindra.clean.architecture.domain.entity.EditTimeDocument
import com.kabindra.clean.architecture.domain.entity.EditTimeRequest
import com.kabindra.clean.architecture.domain.entity.TicketDocumentRemove
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifier
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifierData
import com.kabindra.clean.architecture.domain.entity.UpdateTimeRequest
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.DropdownField
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.filepicker.ExistingTimeFileUploaded
import com.kabindra.clean.architecture.presentation.ui.component.filepicker.FilePicker
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.viewmodel.remote.AttendanceViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.remote.DocumentViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.remote.TimeViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.confirmation.GlobalConfirmationDialog
import com.kabindra.clean.architecture.utils.constants.ConfirmationType
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
import kotlinx.coroutines.launch
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.timeToString
import org.koin.compose.viewmodel.koinViewModel

private var documents: Set<PlatformFile> by mutableStateOf(emptySet())
private var isDocumentFieldValid by mutableStateOf(false)
private var documentFieldError by mutableStateOf("")
private var selectedDocumentToRemove = -1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTimeRequestBottomSheet(
    attendanceViewModel: AttendanceViewModel = koinViewModel(),
    documentViewModel: DocumentViewModel = koinViewModel(),
    timeViewModel: TimeViewModel = koinViewModel(),
    ticketId: String,
    onNavigateLogin: () -> Unit,
    onActionTimeRequestEdited: (needUpdate: Boolean) -> Unit,
    onDismissRequest: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val timeRequestVerifierState by timeViewModel.timeRequestVerifierState.collectAsState()
    val applyTimeRequestState by timeViewModel.applyTimeRequestState.collectAsState()
    val editTimeRequestState by timeViewModel.editTimeRequestState.collectAsState()
    val updateTimeRequestState by timeViewModel.updateTimeRequestState.collectAsState()
    val documentRemoveState by documentViewModel.documentRemoveState.collectAsState()
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
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

    var showNepaliDatePickerDialog by remember { mutableStateOf(false) }
    val defaultNepaliDatePickerState = rememberNepaliDatePickerState()

    var timeRequestDateSelectedField by remember { mutableStateOf("") }
    var isTimeRequestDateValid by remember { mutableStateOf(false) }
    var timeRequestDateError by remember { mutableStateOf("") }

    var checkInTimeField by remember { mutableStateOf("") }
    var checkOutTimeField by remember { mutableStateOf("") }

    var actualInTimeField by remember { mutableStateOf("") }
    var isActualInTimeFieldValid by remember { mutableStateOf(false) }
    var actualInTimeFieldError by remember { mutableStateOf("") }

    var inNoteField by remember { mutableStateOf("") }
    var isInNoteFieldValid by remember { mutableStateOf(false) }
    var inNoteFieldError by remember { mutableStateOf("") }

    var actualOutTimeField by remember { mutableStateOf("") }
    var isActualOutTimeFieldValid by remember { mutableStateOf(false) }
    var actualOutTimeFieldError by remember { mutableStateOf("") }

    var outNoteField by remember { mutableStateOf("") }
    var isOutNoteFieldValid by remember { mutableStateOf(false) }
    var outNoteFieldError by remember { mutableStateOf("") }
    var timeRequestVerifierList by remember {
        mutableStateOf<List<TimeRequestVerifierData>>(
            emptyList()
        )
    }
    var timeRequestVerifierDataField by remember { mutableStateOf<TimeRequestVerifierData?>(null) }
    var isTimeRequestVerifierValid by remember { mutableStateOf(false) }
    var timeRequestVerifierError by remember { mutableStateOf("") }

    var verifierIdField by remember { mutableStateOf("") }

    var existingDocuments by remember { mutableStateOf<List<EditTimeDocument>>(emptyList()) }
    var existingDocumentsFieldError by mutableStateOf("")

    var showConfirmation by remember { mutableStateOf(false) }
    var confirmationMessage by remember { mutableStateOf("") }
    var confirmationType by remember { mutableStateOf<ConfirmationType>(ConfirmationType.None) }

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
            timeViewModel.resetStates()

            showLoading = false
            showSuccess = false
            showError = false

            isTimeRequestDateValid = false
            timeRequestDateError = ""

            isActualInTimeFieldValid = false
            actualInTimeFieldError = ""

            isInNoteFieldValid = false
            inNoteFieldError = ""

            isActualOutTimeFieldValid = false
            outNoteFieldError = ""

            isOutNoteFieldValid = false
            outNoteFieldError = ""

            isTimeRequestVerifierValid = false
            timeRequestVerifierError = ""

            isDocumentFieldValid = false
            documentFieldError = ""
            documents = emptySet()
        }
    }

    LaunchedEffect(Unit) {
        editTimeRequest(timeViewModel, ticket_id = ticketId)
    }

    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = errorStatusCode,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            /*onDismiss = {
                showError = false
                errorStatusCode = -1
                errorTitle = ""
                errorMessage = ""
            },*/
        )
        return
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (timeRequestVerifierList.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = AppTheme.dimens.paddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingSmall),
                    value = timeRequestDateSelectedField,
                    onValueChange = {
                        timeRequestDateSelectedField = it
                        if (timeRequestDateSelectedField.isBlank()) {
                            isTimeRequestDateValid = false
                            timeRequestDateError = "Date must not be empty"
                        } else {
                            isTimeRequestDateValid = true
                            timeRequestDateError = ""
                        }
                    },
                    label = "Date",
                    isError = isTimeRequestDateValid,
                    errorText = timeRequestDateError,
                    imeAction = ImeAction.Next,
                    trialingIcon = Icons.Default.CalendarMonth,
                    isEnabled = false,
                    onClickTrailingIcon = {
                        showNepaliDatePickerDialog = true
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = AppTheme.dimens.paddingSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        text = "Check In",
                        fontWeight = FontWeight.Bold,
                        size = TextSize.Large
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    TextComponent(text = checkInTimeField.takeIf { it.isNotEmpty() }
                        ?: "", fontWeight = FontWeight.Bold, size = TextSize.Large)
                }
                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EditTimePickerField(
                        label = "Actual In time",
                        selectedTime = actualInTimeField,
                        isError = isActualInTimeFieldValid,
                        errorText = actualInTimeFieldError,
                        onTimeSelected = { selectedActualInTime ->
                            actualInTimeField = selectedActualInTime

                            if (actualInTimeField.isBlank()) {
                                isActualInTimeFieldValid = false
                                actualInTimeFieldError = "Actual In time must not be empty"
                            } else {
                                isActualInTimeFieldValid = true
                                isActualOutTimeFieldValid = true
                                actualInTimeFieldError = ""
                                actualOutTimeFieldError = ""
                            }
                        }
                    )

                    InputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.paddingSmall),
                        value = inNoteField,
                        onValueChange = {
                            inNoteField = it
                            if (inNoteField.isBlank()) {
                                isInNoteFieldValid = false
                                inNoteFieldError = "In note must not be empty"
                            } else {
                                isInNoteFieldValid = true
                                inNoteFieldError = ""
                            }
                        },
                        label = "In Note",
                        isError = isInNoteFieldValid,
                        errorText = inNoteFieldError,
                        imeAction = ImeAction.Next,
                        trialingIcon = null,
                        onClickTrailingIcon = {}
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = AppTheme.dimens.paddingSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        text = "Check Out",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    TextComponent(text = checkOutTimeField.takeIf { it.isNotEmpty() }
                        ?: "N/A", fontWeight = FontWeight.Bold, size = TextSize.Large)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EditTimePickerField(
                        label = "Actual Out time",
                        selectedTime = actualOutTimeField,
                        isError = isActualOutTimeFieldValid,
                        errorText = actualOutTimeFieldError,
                        onTimeSelected = { selectedActualOutTime ->
                            actualOutTimeField = selectedActualOutTime

                            if (actualOutTimeField.isBlank()) {
                                isActualOutTimeFieldValid = false
                                actualOutTimeFieldError = "Actual Out time must not be empty"
                            } else {
                                isActualOutTimeFieldValid = true
                                isActualInTimeFieldValid = true
                                actualOutTimeFieldError = ""
                                actualInTimeFieldError = ""
                            }
                        }
                    )
                    InputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.dimens.paddingSmall),
                        value = outNoteField,
                        onValueChange = {
                            outNoteField = it
                            if (outNoteField.isBlank()) {
                                isOutNoteFieldValid = false
                                outNoteFieldError = "Out note must not be empty"
                            } else {
                                isOutNoteFieldValid = true
                                outNoteFieldError = ""
                            }
                        },
                        label = "Out Note",
                        isError = isOutNoteFieldValid,
                        errorText = outNoteFieldError,
                        imeAction = ImeAction.Next,
                        trialingIcon = null,
                        onClickTrailingIcon = {}
                    )
                }

                DropdownField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingSmall),
                    items = timeRequestVerifierList,
                    itemContent = { verifier: TimeRequestVerifierData -> verifier.name!! },
                    value = if (timeRequestVerifierDataField != null) {
                        timeRequestVerifierDataField!!.name!!
                    } else {
                        ""
                    },
                    selectedItem = timeRequestVerifierDataField,
                    onItemSelected = {
                        timeRequestVerifierDataField = it
                        if (timeRequestVerifierDataField == null) {
                            isTimeRequestVerifierValid = false
                            timeRequestVerifierError = "Please select a service"
                        } else {
                            isTimeRequestVerifierValid = true
                            timeRequestVerifierError = ""
                        }
                    },
                    label = "Select Verifier",
                    leadingIcon = null,
                    isError = isTimeRequestVerifierValid,
                    errorText = timeRequestVerifierError
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
                        text = "(pdf, doc, docx,jpeg,png, up to 2MB)",
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

                    ExistingTimeFileUploaded(
                        existingDocuments = existingDocuments,
                        onDocumentsChanged = {
                            selectedDocumentToRemove = it.id ?: -1

                            showConfirmation = true
                            confirmationMessage =
                                "Are you sure you want to remove uploaded document?"
                            confirmationType = ConfirmationType.EditTimeRequestDocumentRemoval

                        },
                        existingDocumentFieldError = existingDocumentsFieldError
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
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
                                /*var isValid = true
                                if (timeRequestDateSelected.isEmpty()) {
                                    isTimeRequestDateValid = false
                                    timeRequestDateError = "Date must not be empty"
                                    isValid = false
                                } else {
                                    isTimeRequestDateValid = true
                                    timeRequestDateError = ""
                                }

                                if (timeRequestVerifierDataField == null) {
                                    isTimeRequestVerifierValid = false
                                    timeRequestVerifierError = "Please select a verifier"
                                    isValid = false
                                } else {
                                    isTimeRequestVerifierValid = true
                                    timeRequestVerifierError = ""
                                }

                                if (actualInTimeField.isEmpty() && actualOutTimeField.isEmpty()) {
                                    isActualInTimeFieldValid = false
                                    isActualOutTimeFieldValid = false
                                    actualInTimeFieldError =
                                        "Either In Time or Out Time is required"
                                    actualOutTimeFieldError =
                                        "Either In Time or Out Time is required"
                                    isValid = false
                                } else {
                                    isActualInTimeFieldValid = true
                                    isActualOutTimeFieldValid = true
                                    actualInTimeFieldError = ""
                                    actualOutTimeFieldError = ""
                                }

                                if (actualInTimeField.isNotEmpty() && inNoteField.isEmpty()) {
                                    isInNoteFieldValid = false
                                    inNoteFieldError =
                                        "The actual in time remarks field is required."
                                    isValid = false
                                } else {
                                    isInNoteFieldValid = true
                                    inNoteFieldError = ""
                                }

                                if (actualOutTimeField.isNotEmpty() && outNoteField.isEmpty()) {
                                    isOutNoteFieldValid = false
                                    outNoteFieldError =
                                        "The actual out time remarks field is required."
                                    isValid = false
                                } else {
                                    isOutNoteFieldValid = true
                                    outNoteFieldError = ""
                                }*/
                                updateTimeRequest(
                                    timeViewModel,
                                    ticket_id = ticketId,
                                    verifier_id = verifierIdField,
                                    nep_date = timeRequestDateSelectedField,
                                    actual_in_time = actualInTimeField,
                                    actual_in_time_remarks = inNoteField,
                                    actual_out_time = actualOutTimeField,
                                    actual_out_time_remarks = outNoteField,
                                    documents = documents.toList()
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
                        timeRequestDateSelectedField = selectedDate
                        isTimeRequestDateValid = true
                        timeRequestDateError = ""
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

    LaunchedEffect(documentRemoveState) {
        when (documentRemoveState) {
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

                val documentRemove: TicketDocumentRemove =
                    (documentRemoveState as Result.Success<TicketDocumentRemove>).data
                successMessage = documentRemove.message
                successType = ResponseType.DocumentRemove

                val updateExistingDocuments =
                    existingDocuments.filter { it.id != selectedDocumentToRemove }
                existingDocuments = updateExistingDocuments

                selectedDocumentToRemove = -1
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (applyTimeRequestState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (applyTimeRequestState as Result.Error).error.message
                errorMessages = (applyTimeRequestState as Result.Error).error.errors
                errorType = ResponseType.None

            }

        }
    }

    LaunchedEffect(timeRequestVerifierState) {
        when (timeRequestVerifierState) {
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

                val timeRequestVerifier: TimeRequestVerifier =
                    (timeRequestVerifierState as Result.Success<TimeRequestVerifier>).data

                timeRequestVerifierList = timeRequestVerifier.response?.verifiers!!
                timeRequestVerifierDataField =
                    timeRequestVerifierList.find { it.id.toString() == verifierIdField }
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (timeRequestVerifierState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (timeRequestVerifierState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(applyTimeRequestState) {
        when (applyTimeRequestState) {
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

                val applyTimeRequest: ApplyTime =
                    (applyTimeRequestState as Result.Success<ApplyTime>).data
                successMessage = applyTimeRequest.message
                successType = ResponseType.ApplyTimeRequest

            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (applyTimeRequestState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (applyTimeRequestState as Result.Error).error.message
                errorMessages = (applyTimeRequestState as Result.Error).error.errors
                errorType = ResponseType.None

                if (!errorMessages.isNullOrEmpty()) {
                    showError = false
                    handleInputFieldErrors(errorMessages) { field, error ->
                        when (field) {
                            InputFieldIdType.TimeRequestDate -> {
                                isTimeRequestDateValid = error == null
                                timeRequestDateError = error ?: ""
                            }

                            InputFieldIdType.TimeRequestActualInTime -> {
                                isActualInTimeFieldValid = error == null
                                actualInTimeFieldError = error ?: ""
                            }

                            InputFieldIdType.TimeRequestActualInTimeRemarks -> {
                                isInNoteFieldValid = error == null
                                inNoteFieldError = error ?: ""
                            }

                            InputFieldIdType.TimeRequestActualOutTime -> {
                                isActualOutTimeFieldValid = error == null
                                outNoteFieldError = error ?: ""
                            }

                            InputFieldIdType.TimeRequestActualOutTimeRemarks -> {
                                isOutNoteFieldValid = error == null
                                outNoteFieldError = error ?: ""
                            }

                            InputFieldIdType.TimeRequestVerifiedId -> {
                                isTimeRequestVerifierValid = error == null
                                timeRequestVerifierError = error ?: ""
                            }

                            InputFieldIdType.LeaveRequestDocuments -> {
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

    LaunchedEffect(editTimeRequestState) {
        when (editTimeRequestState) {
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

                val editTimeRequestResponse: EditTimeRequest =
                    (editTimeRequestState as Result.Success<EditTimeRequest>).data

                timeRequestDateSelectedField = editTimeRequestResponse.response?.nep_date ?: ""
                checkInTimeField = editTimeRequestResponse.response?.check_in_time ?: ""
                checkOutTimeField = editTimeRequestResponse.response?.check_out_time ?: ""
                actualInTimeField = editTimeRequestResponse.response?.actual_in_time ?: ""
                inNoteField = editTimeRequestResponse.response?.actual_in_time_remarks ?: ""
                actualOutTimeField = editTimeRequestResponse.response?.actual_out_time ?: ""
                outNoteField = editTimeRequestResponse.response?.actual_out_time_remarks ?: ""
                verifierIdField = editTimeRequestResponse.response?.verifier_id.toString()

                existingDocuments = editTimeRequestResponse.response?.documents ?: emptyList()

                timeRequestVerifiers(timeViewModel)

            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (editTimeRequestState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (editTimeRequestState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(updateTimeRequestState) {
        when (updateTimeRequestState) {
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

                val updateTimeRequestResponse: UpdateTimeRequest =
                    (updateTimeRequestState as Result.Success<UpdateTimeRequest>).data
                successMessage = updateTimeRequestResponse.message
                successType = ResponseType.UpdateTimeRequest

            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (updateTimeRequestState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (updateTimeRequestState as Result.Error).error.message
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
                /*scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onDismissRequest()
                    }
                }*/

                if (successType == ResponseType.UpdateTimeRequest) {
                    onActionTimeRequestEdited(true)
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
            onNavigateLogin = { onNavigateLogin() })
    }

    if (showConfirmation) {
        GlobalConfirmationDialog(
            isVisible = showConfirmation,
            isAction = true,
            message = confirmationMessage,
            onCancel = {
                showConfirmation = false
            },
            onContinue = {
                showConfirmation = false

                if (confirmationType == ConfirmationType.EditTimeRequestDocumentRemoval) {
                    /* removeDocumentsField.add(selectedDocumentToRemove)
                     existingDocuments =
                         existingDocuments.filter { it.id != selectedDocumentToRemove }
                     selectedDocumentToRemove = -1*/

                    getDocumentRemove(
                        documentViewModel,
                        ticketId,
                        selectedDocumentToRemove.toString()
                    )
                }

            }
        )
    }
}

@Composable
fun EditTimePickerField(
    label: String,
    selectedTime: String,
    isError: Boolean = false,
    errorText: String = "",
    onTimeSelected: (String) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    if (showTimePicker) {
        WheelTimePickerView(
            modifier = Modifier.fillMaxWidth(),
            showTimePicker = showTimePicker,
            titleStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
            ),
            doneLabelStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF007AFF),
            ),
            textColor = Color(0xff007AFF),
            timeFormat = TimeFormat.HOUR_24,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                borderColor = Color.LightGray,
            ),
            rowCount = 5,
            height = 170.dp,
            textStyle = TextStyle(
                fontWeight = FontWeight(600),
            ),
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
            dragHandle = {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(50.dp)
                        .clip(CircleShape),
                    thickness = 4.dp,
                    color = Color(0xFFE8E4E4)
                )
            },
            shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
            onDoneClick = {
                onTimeSelected(timeToString(it, "hh:mm"))
                showTimePicker = false
            },
            onDismiss = {
                showTimePicker = false
            }
        )
    }
    InputField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingSmall)
            .clickable { showTimePicker = true },
        value = selectedTime,
        onValueChange = { },
        label = label,
        isError = isError,
        errorText = errorText,
        imeAction = ImeAction.Next,
        trialingIcon = null,
        isEnabled = false,
        onClickTrailingIcon = { showTimePicker = true }
    )

}


private fun timeRequestVerifiers(timeViewModel: TimeViewModel) {
    timeViewModel.getTimeRequestVerifiers()
}

private fun editTimeRequest(timeViewModel: TimeViewModel, ticket_id: String) {
    timeViewModel.getTimeRequestEdit(ticket_id)
}

private fun updateTimeRequest(
    timeViewModel: TimeViewModel,
    ticket_id: String,
    verifier_id: String,
    nep_date: String,
    actual_in_time: String,
    actual_in_time_remarks: String,
    actual_out_time: String,
    actual_out_time_remarks: String,
    documents: List<PlatformFile>
) {
    timeViewModel.getUpdateTimeRequest(
        TimeRequestUpdateDataRequest(
            ticket_id,
            verifier_id,
            nep_date,
            actual_in_time,
            actual_in_time_remarks,
            actual_out_time,
            actual_out_time_remarks,
            documents
        )
    )
}

private fun getDocumentRemove(
    documentViewModel: DocumentViewModel,
    ticket_id: String,
    document_id: String
) {
    documentViewModel.getDocumentRemove(TicketDocumentRemoveRequest(ticket_id, document_id))
}

