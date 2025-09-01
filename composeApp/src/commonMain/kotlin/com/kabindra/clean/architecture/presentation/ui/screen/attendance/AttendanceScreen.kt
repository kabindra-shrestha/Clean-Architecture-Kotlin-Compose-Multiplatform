package com.kabindra.clean.architecture.presentation.ui.screen.attendance

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.data.request.AttendanceDataRequest
import com.kabindra.clean.architecture.domain.entity.Attendance
import com.kabindra.clean.architecture.domain.entity.AttendanceFilter
import com.kabindra.clean.architecture.domain.entity.AttendanceInfoData
import com.kabindra.clean.architecture.domain.entity.Month
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.DropdownField
import com.kabindra.clean.architecture.presentation.ui.component.HorizontalPagersWithTabs
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.component.ShowEmpty
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TopAppBarWithBackComponent
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.ui.theme.attendanceDefault
import com.kabindra.clean.architecture.presentation.ui.theme.attendanceToday
import com.kabindra.clean.architecture.presentation.ui.theme.onBackgroundLight
import com.kabindra.clean.architecture.presentation.viewmodel.remote.AttendanceViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.AttendanceContentType
import com.kabindra.clean.architecture.utils.enums.AttendanceStatus
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import dev.shivathapaa.nepalidatepickerkmp.calendar_model.NepaliDateConverter
import dev.shivathapaa.nepalidatepickerkmp.rememberNepaliDatePickerState
import org.koin.compose.viewmodel.koinViewModel

var date: String = ""

@Composable
fun AttendanceScreen(
    attendanceViewModel: AttendanceViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit,
    onBackNavigate: () -> Unit,
) {
    val attendanceState by attendanceViewModel.attendanceState.collectAsState()
    val attendanceFilterState by attendanceViewModel.attendanceFilterState.collectAsState()
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var showEmpty by remember { mutableStateOf(false) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }

    var attendanceDataList: MutableList<AttendanceInfoData> = mutableListOf()
    var attendanceData by remember { mutableStateOf(attendanceDataList) }
    var yearList by remember { mutableStateOf<List<Int>>(emptyList()) }
    var monthList by remember { mutableStateOf<List<Month>>(emptyList()) }

    val defaultNepaliDatePickerState = rememberNepaliDatePickerState()
    val selectedNepaliDate = defaultNepaliDatePickerState.displayedMonth
    val currentNepaliYear = selectedNepaliDate.year
    val currentNepaliMonth = selectedNepaliDate.month
    var selectedYear = remember { mutableStateOf(currentNepaliYear) }
    var selectedMonth = remember {
        mutableStateOf(
            Month(
                id = currentNepaliMonth,
                name = NepaliDateConverter.getMonthName(currentNepaliMonth)
            )
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            attendanceViewModel.resetStates()
            showLoading = false
            showSuccess = false
            showError = false
        }
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

    LaunchedEffect(Unit) {
        attendanceFilter(attendanceViewModel)
        attendance(
            attendanceViewModel,
            year = selectedYear.value.toString(),
            month = formatWithLeadingZero(selectedMonth.value.id.toString())
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(
            top = 12.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = AppTheme.dimens.bottomNavigationPadding
        ),
    ) {
        TopAppBarWithBackComponent(
            title = "Attendance",
            onBackNavigate = { onBackNavigate() })
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val tabs = AttendanceContentType.entries.map { it.title }
            val pageItems = AttendanceContentType.entries.map { it.slug }

            HorizontalPagersWithTabs(
                modifier = Modifier.fillMaxSize(),
                padding = 0.dp,
                contentPadding = PaddingValues(0.dp),
                pageSpacing = 0.dp,
                useGraphicsLayer = true,
                tabs = tabs,
                pageItems = pageItems
            ) { currentPage, page, pageContent ->
                when (pageContent) {
                    AttendanceContentType.MyAttendance.slug -> {
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .padding(top = AppTheme.dimens.paddingSmall)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (yearList.isNotEmpty()) {
                                    DropdownField(
                                        modifier = Modifier.fillMaxWidth().weight(0.4f)
                                            .padding(end = 5.dp),
                                        items = yearList,
                                        itemContent = { year -> year.toString() },
                                        value = selectedYear.value.toString(),
                                        selectedItem = selectedYear.value,
                                        onItemSelected = {
                                            selectedYear.value = it
                                            attendance(
                                                attendanceViewModel,
                                                year = selectedYear.value.toString(),
                                                month = formatWithLeadingZero(
                                                    selectedMonth.value.id.toString()
                                                )
                                            )
                                        },
                                        label = "Select Year",
                                        leadingIcon = null,
                                        isError = false,
                                    )
                                }

                                if (monthList.isNotEmpty()) {
                                    DropdownField(
                                        modifier = Modifier.fillMaxWidth().weight(0.4f)
                                            .padding(start = 5.dp),
                                        items = monthList,
                                        itemContent = { month -> month.name.toString() },
                                        value = selectedMonth.value.name.toString(),
                                        selectedItem = selectedMonth.value,
                                        onItemSelected = {
                                            selectedMonth.value = it
                                            attendance(
                                                attendanceViewModel,
                                                year = selectedYear.value.toString(),
                                                month = formatWithLeadingZero(
                                                    selectedMonth.value.id.toString()
                                                )
                                            )
                                        },
                                        label = "Select Month",
                                        leadingIcon = null,
                                        isError = false,
                                    )
                                }
                            }

                            if (attendanceData.isNotEmpty()) {
                                BaseLazy(
                                    modifier = Modifier
                                        .wrapContentHeight(),
                                    contentPadding = PaddingValues(0.dp),
                                    arrangement = Arrangement.spacedBy(0.dp),
                                    items = attendanceData,
                                    listType = LazyListType.LIST,
                                    scrollDirection = LazyScrollDirection.VERTICAL,
                                    itemContent = { index, item ->
                                        AttendanceListItem(
                                            item,
                                            onNavigateLogin = { onNavigateLogin() },
                                            onApplied = {
                                                attendanceFilter(attendanceViewModel)
                                            }
                                        )
                                    },
                                    onLoadMore = { },
                                    onScrollStateChanged = { },
                                )
                            } else {
                                if (showEmpty) {
                                    ShowEmpty()
                                }
                            }
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
    }

    LaunchedEffect(attendanceState) {
        when (attendanceState) {
            is Result.Initial -> Unit

            is Result.Loading -> {
                showLoading = true
                showSuccess = false
                showError = false
                showEmpty = false
            }

            is Result.Success -> {
                showLoading = false
                showSuccess = true
                showError = false

                val attendance: Attendance =
                    (attendanceState as Result.Success<Attendance>).data
                attendanceDataList =
                    (attendance.response!!.attendance ?: emptyList()).toMutableList()
                attendanceData = attendanceDataList

                if (attendanceData.isEmpty()) {
                    showEmpty = true
                }
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                showEmpty = false
                errorStatusCode = (attendanceState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (attendanceState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    LaunchedEffect(attendanceFilterState) {
        when (attendanceFilterState) {
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

                var attendanceFilter =
                    (attendanceFilterState as Result.Success<AttendanceFilter>).data
                yearList = attendanceFilter.response?.yearList!!
                monthList = attendanceFilter.response?.monthList!!
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                errorStatusCode = (attendanceFilterState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (attendanceFilterState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
    }

    /*  if (showSuccess) {
          GlobalSuccessDialog(
              isVisible = showSuccess,
              isAction = true,
              message = successMessage,
              onDismiss = {
                  *//*if (successType == ResponseType.MPINSet) {
                    onNavigateDashboard()
                }*//*
            })
    }*/

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
}

@Composable
fun AttendanceListItem(
    attendance: AttendanceInfoData,
    onNavigateLogin: () -> Unit,
    onApplied: () -> Unit
) {
    var showTimeRequestBottomSheet by remember { mutableStateOf(false) }
    var showLeaveBottomSheet by remember { mutableStateOf(false) }
    var attendanceDate = ""

    var todayEnglishCalendarNow = NepaliDateConverter.todayEnglishCalendar
    val formattedDate = "${todayEnglishCalendarNow.year}-${
        todayEnglishCalendarNow.month.toString().padStart(2, '0')
    }-${todayEnglishCalendarNow.dayOfMonth.toString().padStart(2, '0')}"

    /* val attendanceStatusEnum =
         AttendanceStatus.entries.find { it.attendanceStatus == attendance.status }
             ?: AttendanceStatus.Present*/

    val attendanceStatusEnum = AttendanceStatus.entries.find {
        attendance.status!!.contains(it.attendanceStatus, ignoreCase = true)
    } ?: AttendanceStatus.Present

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.paddingSmall,
                vertical = AppTheme.dimens.paddingSmall
            )
    ) {
        CardBorderInside(
            modifier = Modifier.fillMaxWidth(),
            sides = listOf(),
            containerColor = if (formattedDate == attendance.date_en) {
                attendanceToday
            } else {
                attendanceDefault
            }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.paddingNormal)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextComponent(text = attendance.date_en.takeIf { it!!.isNotEmpty() }
                        ?: "N/A", fontWeight = FontWeight.Bold)

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
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = attendanceStatusEnum.getColor(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    TextComponent(text = attendance.status.takeIf { it!!.isNotEmpty() }
                        ?: "N/A", fontWeight = FontWeight.Bold)
                }

                AnimatedVisibility(visible = isExpanded) {
                    Column(modifier = Modifier.padding(top = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                .padding(
                                    horizontal = AppTheme.dimens.paddingSmall,
                                    vertical = AppTheme.dimens.paddingNormal
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            Box(
                                modifier = Modifier
                                    .weight(1f).padding(end = AppTheme.dimens.paddingSmall),
                                contentAlignment = Alignment.Center
                            ) {
                                TextComponent(
                                    text = buildAnnotatedString {
                                        append("Login: ")
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(attendance.in_time.takeIf { it!!.isNotEmpty() }
                                                ?: "N/A")
                                        }
                                    }, maxLines = 2
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                                    .background(Color.LightGray)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = AppTheme.dimens.paddingSmall),
                                contentAlignment = Alignment.Center
                            ) {
                                TextComponent(
                                    text = buildAnnotatedString {
                                        append("Logout: ")
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(attendance.out_time.takeIf { it!!.isNotEmpty() }
                                                ?: "N/A")
                                        }
                                    }, maxLines = 2
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        TextComponent(
                            text = buildAnnotatedString {
                                append("Working Hours: ")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(attendance.total_hours.takeIf { it!!.isNotEmpty() }
                                        ?: "N/A")
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        TextComponent(
                            text = buildAnnotatedString {
                                append("Source: ")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(attendance.source.takeIf { it!!.isNotEmpty() } ?: "N/A")
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        TextComponent(
                            text = buildAnnotatedString {
                                append("Remarks:")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(attendance.remarks.takeIf { it!!.isNotEmpty() } ?: "N/A")
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Column(modifier = Modifier.padding(bottom = 4.dp)) {
                            if (attendance.time_request != null || attendance.leave_request != null) {
                                if (attendance.time_request!! || attendance.leave_request!!) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {

                                        if (attendance.time_request) {
                                            ButtonTextAndIcon(
                                                modifier = if (attendance.leave_request!!) {
                                                    Modifier.weight(1f)
                                                        .padding(end = 4.dp)
                                                } else {
                                                    Modifier.fillMaxWidth(0.5f)
                                                        .padding(start = 8.dp)
                                                },
                                                text = "Missed Punch",
                                                iconContentDescription = "Missed Punch",
                                                onClick = {
                                                    attendanceDate = attendance.date_np!!
                                                    showTimeRequestBottomSheet = true
                                                }
                                            )
                                        }

                                        if (attendance.leave_request!!) {
                                            ButtonTextAndIcon(
                                                modifier = if (attendance.time_request) {
                                                    Modifier.weight(1f)
                                                        .padding(start = 4.dp, end = 2.dp)
                                                } else {
                                                    Modifier.fillMaxWidth(0.5f)
                                                        .padding(start = 8.dp)
                                                },
                                                text = "Leave",
                                                iconContentDescription = "Leave",
                                                onClick = {
                                                    attendanceDate = attendance.date_np!!
                                                    showLeaveBottomSheet = true
                                                }
                                            )
                                        }
                                        if (showTimeRequestBottomSheet) {
                                            ModalBottomSheetComponent(
                                                modifier = Modifier.padding(
                                                    AppTheme.dimens.paddingSmall
                                                ),
                                                title = "Apply Time Request",
                                                isVisible = showTimeRequestBottomSheet,
                                                isExpanded = true,
                                                icon = Icons.Default.Close,  // Pass the icon you want to use for closing
                                                onDismiss = { showTimeRequestBottomSheet = false },
                                                content = {
                                                    TimeRequestBottomSheet(
                                                        attendanceDate = attendanceDate,
                                                        attendanceLogin = attendance.in_time!!,
                                                        attendanceLogout = attendance.out_time!!,
                                                        onNavigateLogin = { onNavigateLogin() },
                                                        onTimeRequestApplied = {
                                                            showTimeRequestBottomSheet = false

                                                            onApplied()
                                                        },
                                                        onDismissRequest = {
                                                            showTimeRequestBottomSheet = false
                                                        })
                                                }
                                            )
                                        }

                                        if (showLeaveBottomSheet) {
                                            ModalBottomSheetComponent(
                                                modifier = Modifier.padding(
                                                    AppTheme.dimens.paddingSmall
                                                ),
                                                title = "Apply Leave Request",
                                                isVisible = showLeaveBottomSheet,
                                                isExpanded = true,
                                                icon = Icons.Default.Close,  // Pass the icon you want to use for closing
                                                onDismiss = { showLeaveBottomSheet = false },
                                                content = {
                                                    LeaveRequestBottomSheet(
                                                        attendanceDate = attendanceDate,
                                                        onNavigateLogin = { onNavigateLogin() },
                                                        onLeaveRequestApplied = {
                                                            showLeaveBottomSheet = false

                                                            onApplied()
                                                        },
                                                        onDismissRequest = {
                                                            showLeaveBottomSheet = false
                                                        })
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

            /* Column(
                 modifier = Modifier.fillMaxWidth()
                     .padding(start = 10.dp, end = 10.dp)
             ) {
                 Card(
                     modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 8.dp),
                     colors = CardDefaults.cardColors(
                         containerColor = attendanceStatusEnum.getColor()
                     )
                 ) {
                     Column(
                         modifier = Modifier.fillMaxWidth()
                     ) {
                         Box(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .height(5.dp)
                         )
                         Row(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(8.dp)
                         ) {
                             Column(
                                 modifier = Modifier
                                     .weight(1f)
                                     .padding(end = 8.dp),
                                 horizontalAlignment = Alignment.Start
                             ) {
                                 TextComponent(
                                     text = "Date",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(4.dp))

                                 TextMedium(
                                     text = "Login",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(2.dp))
                                 TextComponent(text = attendance.in_time.takeIf { it!!.isNotEmpty() }
                                     ?: "N/A")
                                 Spacer(modifier = Modifier.height(4.dp))

                                 TextMedium(
                                     text = "Working Hours",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(2.dp))
                                 TextMedium(text = attendance.total_hours.takeIf { it!!.isNotEmpty() }
                                     ?: "N/A")
                                 Spacer(modifier = Modifier.height(4.dp))

                                 TextMedium(
                                     text = "Remarks",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(2.dp))
                                 TextMedium(text = attendance.remarks.takeIf { it!!.isNotEmpty() }
                                     ?: "N/A")
                                 Spacer(modifier = Modifier.height(2.dp))
                             }
                             Column(
                                 modifier = Modifier
                                     .weight(1f)
                                     .padding(start = 8.dp),
                                 horizontalAlignment = Alignment.Start
                             ) {
                                 TextMedium(text = "${attendance.date_en.takeIf { it!!.isNotEmpty() } ?: "N/A"} ( ${attendance.date_np.takeIf { it!!.isNotEmpty() } ?: "N/A"} )")
                                 Spacer(modifier = Modifier.height(4.dp))

                                 TextMedium(
                                     text = "Logout",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(2.dp))
                                 TextMedium(text = attendance.out_time.takeIf { it!!.isNotEmpty() }
                                     ?: "N/A")
                                 Spacer(modifier = Modifier.height(4.dp))

                                 TextMedium(
                                     text = "Status",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(2.dp))
                                 TextMedium(text = attendance.status.takeIf { it!!.isNotEmpty() }
                                     ?: "N/A")
                                 Spacer(modifier = Modifier.height(4.dp))

                                 TextMedium(
                                     text = "Source",
                                     fontWeight = FontWeight.Bold
                                 )
                                 Spacer(modifier = Modifier.height(2.dp))
                                 TextMedium(text = attendance.source.takeIf { it!!.isNotEmpty() }
                                     ?: "N/A")
                                 Spacer(modifier = Modifier.height(2.dp))
                             }
                         }
                         Column(modifier = Modifier.padding(bottom = 4.dp)) {
                             if (attendance.time_request != null || attendance.leave_request != null) {
                                 if (attendance.time_request!! || attendance.leave_request!!) {
                                     TextMedium(
                                         modifier = Modifier.padding(start = 10.dp),
                                         text = "Apply",
                                         fontWeight = FontWeight.Bold
                                     )
                                     Row(
                                         modifier = Modifier.fillMaxWidth(),
                                     ) {

                                         if (attendance.time_request) {
                                             ButtonTextAndIcon(
                                                 modifier = if (attendance.leave_request!!) {
                                                     Modifier.weight(1f)
                                                         .padding(start = 8.dp, end = 4.dp)
                                                 } else {
                                                     Modifier.fillMaxWidth(0.5f)
                                                         .padding(start = 8.dp)
                                                 },
                                                 text = "Missed Punch",
                                                 iconContentDescription = "Missed Punch",
                                                 onClick = {
                                                     attendanceDate = attendance.date_np!!
                                                     showTimeRequestBottomSheet = true
                                                 }
                                             )
                                         }

                                         if (attendance.leave_request!!) {
                                             ButtonTextAndIcon(
                                                 modifier = if (attendance.time_request) {
                                                     Modifier.weight(1f)
                                                         .padding(start = 4.dp, end = 8.dp)
                                                 } else {
                                                     Modifier.fillMaxWidth(0.5f)
                                                         .padding(start = 8.dp)
                                                 },
                                                 text = "Leave",
                                                 iconContentDescription = "Leave",
                                                 onClick = {
                                                     attendanceDate = attendance.date_np!!
                                                     showLeaveBottomSheet = true
                                                 }
                                             )
                                         }
                                         if (showTimeRequestBottomSheet) {
                                             ModalBottomSheetComponent(
                                                 title = "Apply Time Request",
                                                 isVisible = showTimeRequestBottomSheet,
                                                 isExpanded = true,
                                                 icon = Icons.Default.Close,  // Pass the icon you want to use for closing
                                                 onDismiss = { showTimeRequestBottomSheet = false },
                                                 content = {
                                                     TimeRequestBottomSheet(
                                                         attendanceDate = attendanceDate,
                                                         attendanceLogin = attendance.in_time!!,
                                                         attendanceLogout = attendance.out_time!!,
                                                         onNavigateLogin = { onNavigateLogin() },
                                                         onTimeRequestApplied = {
                                                             showTimeRequestBottomSheet = false

                                                             onApplied()
                                                         },
                                                         onDismissRequest = {
                                                             showTimeRequestBottomSheet = false
                                                         })
                                                 }
                                             )
                                         }

                                         if (showLeaveBottomSheet) {
                                             ModalBottomSheetComponent(
                                                 title = "Apply Leave Request",
                                                 isVisible = showLeaveBottomSheet,
                                                 isExpanded = true,
                                                 icon = Icons.Default.Close,  // Pass the icon you want to use for closing
                                                 onDismiss = { showLeaveBottomSheet = false },
                                                 content = {
                                                     LeaveRequestBottomSheet(
                                                         attendanceDate = attendanceDate,
                                                         onNavigateLogin = { onNavigateLogin() },
                                                         onLeaveRequestApplied = {
                                                             showLeaveBottomSheet = false

                                                             onApplied()
                                                         },
                                                         onDismissRequest = {
                                                             showLeaveBottomSheet = false
                                                         })
                                                 }
                                             )
                                         }
                                     }
                                 }
                             }
                         }
                     }
                 }
             }*/
        }
    }

}

fun formatWithLeadingZero(value: String): String {
    val intValue =
        value.toIntOrNull() ?: return value
    return if (intValue < 10) "0$value" else value
}

private fun attendanceFilter(attendanceViewModel: AttendanceViewModel) {
    attendanceViewModel.getAttendanceFilter()
}

private fun attendance(attendanceViewModel: AttendanceViewModel, year: String, month: String) {
    attendanceViewModel.getAttendance(AttendanceDataRequest(year, month))
}



