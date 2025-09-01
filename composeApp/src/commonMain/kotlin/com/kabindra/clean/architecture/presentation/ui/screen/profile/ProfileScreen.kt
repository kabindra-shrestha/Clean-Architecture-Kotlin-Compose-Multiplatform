package com.kabindra.clean.architecture.presentation.ui.screen.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.BiometricInformation
import com.kabindra.clean.architecture.domain.entity.Document
import com.kabindra.clean.architecture.domain.entity.EducationInformation
import com.kabindra.clean.architecture.domain.entity.Experience
import com.kabindra.clean.architecture.domain.entity.Profile
import com.kabindra.clean.architecture.domain.entity.Training
import com.kabindra.clean.architecture.presentation.ui.component.HorizontalPagersWithTabs
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ShowEmpty
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.TopAppBarWithBackComponent
import com.kabindra.clean.architecture.presentation.ui.component.parallaxLayoutModifier
import com.kabindra.clean.architecture.presentation.ui.items.ItemProfileInformation
import com.kabindra.clean.architecture.presentation.ui.items.ItemProfilePersonalInformation
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.ui.theme.imageBorder
import com.kabindra.clean.architecture.presentation.ui.theme.onBackgroundLight
import com.kabindra.clean.architecture.presentation.viewmodel.remote.ProfileViewModel
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.confirmation.GlobalDialogComponent
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.enums.ProfileContentType
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import com.kabindra.clean.architecture.utils.ktor.Result
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit,
    onBackNavigate: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    val profileState by profileViewModel.profileState.collectAsState()
    var showLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var showEmpty by remember { mutableStateOf(false) }
    var errorStatusCode by remember { mutableStateOf(-1) }
    var errorTitle by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var errorType by remember { mutableStateOf<ResponseType>(ResponseType.None) }

    var profile by remember { mutableStateOf<Profile?>(null) }

    var documentList: MutableList<Document> = mutableListOf()
    var documentData by remember { mutableStateOf(documentList) }

    var trainingList: MutableList<Training> = mutableListOf()
    var trainingData by remember { mutableStateOf(trainingList) }

    var experienceList: MutableList<Experience> = mutableListOf()
    var experienceData by remember { mutableStateOf(experienceList) }

    var showProfileDialog by remember { mutableStateOf(false) }


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
        profile(profileViewModel)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(
                top = 12.dp,
                start = 12.dp,
                end = 12.dp, bottom = AppTheme.dimens.bottomNavigationPadding
            ),
    ) {
        TopAppBarWithBackComponent(
            title = "Profile",
            onBackNavigate = { onBackNavigate() })

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (profile != null) {
                LazyColumn(
                    state = scrollState
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp)
                                .parallaxLayoutModifier(scrollState, 2),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ImageHandlerURL(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .border(
                                                2.dp,
                                                imageBorder,
                                                CircleShape
                                            )
                                            .aspectRatio(1f / 1f),
                                        image = profile?.response?.general_information?.profile_img.takeIf { !it.isNullOrEmpty() }
                                            ?: "",
                                        contentDescription = "Profile Icon",
                                        contentScale = ContentScale.Crop,
                                        placeholder = Icons.Default.Person,
                                        circular = true
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))

                                Column(verticalArrangement = Arrangement.Center) {
                                    TextComponent(
                                        text = "${profile?.response?.general_information?.name ?: ""}, (${profile?.response?.general_information?.employee_code ?: ""})",
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextComponent(text = profile?.response?.general_information?.department.takeIf { !it.isNullOrEmpty() }
                                        ?: "N/A", size = TextSize.Medium)
                                }
                                Spacer(modifier = Modifier.weight(1f))

                                ImageHandlerVector(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            showProfileDialog = true
                                        },
                                    image = Icons.Default.SentimentSatisfied,
                                    tint = onBackgroundLight,
                                )
                            }


                        }
                        if (showProfileDialog) {
                            GlobalDialogComponent(
                                isVisible = true,
                                onDismissRequest = { showProfileDialog = false },
                                content = {
                                    ItemProfileInformation(profile?.response?.general_information)
                                })
                        }
                    }


                    /*item {
                        ItemProfileInformation(profile?.response?.general_information)
                    }*/

                    item {
                        val tabs = ProfileContentType.entries.map { it.title }
                        val pageItems = ProfileContentType.entries.map { it.slug }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(
                                    start = AppTheme.dimens.paddingSmall,
                                    top = AppTheme.dimens.paddingSmall,
                                    end = AppTheme.dimens.paddingSmall,
                                    bottom = AppTheme.dimens.paddingNormal
                                )
                        ) {
                            HorizontalPagersWithTabs(
                                modifier = Modifier.fillMaxSize(),
                                padding = 0.dp,
                                contentPadding = PaddingValues(0.dp),
                                pageSpacing = 0.dp,
                                useGraphicsLayer = true,
                                tabs = tabs,
                                tabsIcon = true,
                                pageItems = pageItems
                            ) { currentPage, page, pageContent ->
                                when (pageContent) {
                                    ProfileContentType.Profile.slug -> {
                                        ItemProfilePersonalInformation(
                                            profile?.response?.profile?.personal_information,
                                            profile?.response?.profile?.family_information,
                                            profile?.response?.profile?.emergency_contact_information,
                                            profile?.response?.profile?.experience,
                                            profile?.response?.profile?.education_information
                                        )
                                    }

                                    ProfileContentType.Documents.slug -> {}
                                    ProfileContentType.Training.slug -> {}
                                    ProfileContentType.Misc.slug -> {}
                                    ProfileContentType.SalarySlip.slug -> {}
                                    ProfileContentType.PaySlip.slug -> {}
                                    ProfileContentType.Approver.slug -> {}
                                }
                            }
                        }
                    }
                }
                /*Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                val tabs = ProfileContentType.entries.map { it.title }
                val pageItems = ProfileContentType.entries.map { it.slug }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 8.dp, bottom = 50.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    HorizontalPagersWithTabs(
                        contentPadding = PaddingValues(4.dp),
                        useGraphicsLayer = true,
                        tabs = tabs,
                        pageItems = pageItems,
                        pageContent = { index, content ->
                            when (content) {
                                ProfileContentType.Profile.slug -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            // .verticalScroll(rememberScrollState())
                                    ) {
                                        TextLarge(
                                            modifier = Modifier.padding(4.dp),
                                            text = "Personal Information",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Card(modifier = Modifier.fillMaxWidth()) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(5.dp)
                                                )
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .weight(0.7f)
                                                                .padding(end = 8.dp),
                                                            horizontalAlignment = Alignment.Start
                                                        ) {
                                                            TextMedium(
                                                                text = "Phone:",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Email:",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Marital Status:",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Nationality",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Citizenship No.",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Permanent Address",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Temporary Address ",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                        Column(
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .padding(start = 8.dp),
                                                            horizontalAlignment = Alignment.Start
                                                        ) {
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.phone.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.email.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.marital_status.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.nationality.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.citizenship_no.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.permanent_address.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.personal_information?.temporary_address.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        TextLarge(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "Family Information",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Card(modifier = Modifier.fillMaxWidth()) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(5.dp)
                                                )
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .weight(0.7f)
                                                                .padding(end = 8.dp),
                                                            horizontalAlignment = Alignment.Start
                                                        ) {
                                                            TextMedium(
                                                                text = "Father",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Mother",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "GrandFather",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                        Column(
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .padding(start = 8.dp),
                                                            horizontalAlignment = Alignment.Start
                                                        ) {
                                                            TextMedium(text = profile?.response?.profile?.family_information?.father.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.family_information?.mother.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.family_information?.grandfather.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        TextLarge(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "Emergency Contact Information",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Card(modifier = Modifier.fillMaxWidth()) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(5.dp)
                                                )
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .weight(0.7f)
                                                                .padding(end = 8.dp),
                                                            horizontalAlignment = Alignment.Start
                                                        ) {
                                                            TextMedium(
                                                                text = "Name",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Phone",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(
                                                                text = "Relation",
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                        Column(
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .padding(start = 8.dp),
                                                            horizontalAlignment = Alignment.Start
                                                        ) {
                                                            TextMedium(text = profile?.response?.profile?.emergency_contact_information?.name.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.emergency_contact_information?.phone.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                            Spacer(modifier = Modifier.height(4.dp))
                                                            TextMedium(text = profile?.response?.profile?.emergency_contact_information?.relation.takeIf { !it.isNullOrEmpty() }
                                                                ?: "N/A")
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                         Column(modifier = Modifier.fillMaxSize()) {
                                             BaseLazy(
                                                 modifier = Modifier.fillMaxSize(),
                                                 contentPadding = PaddingValues(0.dp),
                                                 arrangement = Arrangement.spacedBy(0.dp),
                                                 items = experienceData,
                                                 listType = LazyListType.LIST,
                                                 scrollDirection = LazyScrollDirection.VERTICAL,
                                                 itemContent = { index, item ->
                                                     ExperienceData(item)
                                                 },
                                                 isLoading = false,
                                                 loadingContent = { },
                                                 onLoadMore = { },
                                                 onScrollStateChanged = { }
                                             )
                                         }
                                    }

                                }

                                ProfileContentType.Documents.slug -> {
                                    TextLarge(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = "Employee Documents",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(bottom = 55.dp)
                                    ) {
                                        BaseLazy(
                                            modifier = Modifier.fillMaxSize(),
                                            contentPadding = PaddingValues(0.dp),
                                            arrangement = Arrangement.spacedBy(0.dp),
                                            items = documentData,
                                            listType = LazyListType.LIST,
                                            scrollDirection = LazyScrollDirection.VERTICAL,
                                            itemContent = { index, item ->
                                                DocumentItem(item)
                                            },
                                            isLoading = false,
                                            loadingContent = { },
                                            onLoadMore = { },
                                            onScrollStateChanged = { }
                                        )
                                    }
                                }

                                ProfileContentType.Training.slug -> {
                                    TextLarge(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = "Training Information",
                                        fontWeight = FontWeight.Bold
                                    )

                                    Column(modifier = Modifier.fillMaxSize()) {
                                        BaseLazy(
                                            modifier = Modifier.fillMaxSize(),
                                            contentPadding = PaddingValues(0.dp),
                                            arrangement = Arrangement.spacedBy(0.dp),
                                            items = trainingData,
                                            listType = LazyListType.LIST,
                                            scrollDirection = LazyScrollDirection.VERTICAL,
                                            itemContent = { index, item ->
                                                TrainingItem(item)
                                            },
                                            isLoading = false,
                                            loadingContent = { },
                                            onLoadMore = { },
                                            onScrollStateChanged = { }
                                        )
                                    }
                                }

                                ProfileContentType.Misc.slug -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            // .verticalScroll(rememberScrollState())
                                    ) {
                                        TextLarge(
                                            modifier = Modifier.padding(4.dp),
                                            text = "Bank Information",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Card(modifier = Modifier.fillMaxWidth()) {
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
                                                        TextMedium(
                                                            text = "Bank",
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(
                                                            text = "Account No.",
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(
                                                            text = "Outsource",
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(start = 8.dp),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {
                                                        TextMedium(text = profile?.response?.misc?.bank_information?.bank.takeIf { !it.isNullOrEmpty() }
                                                            ?: "N/A")
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(text = profile?.response?.misc?.bank_information?.account_number.takeIf { !it.isNullOrEmpty() }
                                                            ?: "N/A")
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(text = profile?.response?.misc?.bank_information?.outsource_company_name.takeIf { !it.isNullOrEmpty() }
                                                            ?: "N/A")
                                                    }
                                                }
                                            }
                                        }
                                        TextLarge(
                                            modifier = Modifier.padding(top = 8.dp),
                                            text = "Retirement fund",
                                            fontWeight = FontWeight.Bold
                                        )
                                        Card(modifier = Modifier.fillMaxWidth()) {
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
                                                        TextMedium(
                                                            text = "Retirement Fund Name",
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(
                                                            text = "RF No.",
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(
                                                            text = " CIT",
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(start = 8.dp),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {
                                                        TextMedium(text = profile?.response?.misc?.retirement_fund?.retirement_fund_name.takeIf { !it.isNullOrEmpty() }
                                                            ?: "N/A")
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(text = profile?.response?.misc?.retirement_fund?.retirement_fund_nuumber.takeIf { !it.isNullOrEmpty() }
                                                            ?: "N/A")
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        TextMedium(text = profile?.response?.misc?.retirement_fund?.cit_number.takeIf { !it.isNullOrEmpty() }
                                                            ?: "N/A")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                ProfileContentType.SalarySlip.slug -> {
                                    Card(modifier = Modifier.fillMaxWidth()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .weight(0.7f)
                                                    .padding(end = 8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                TextMedium(
                                                    text = "Phone:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                TextMedium(
                                                    text = "Email:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                TextMedium(
                                                    text = "Marital Status:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                TextMedium(
                                                    text = "Nationality:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                TextMedium(
                                                    text = "Citizenship No.:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                TextMedium(
                                                    text = "Permanent Address:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                TextMedium(
                                                    text = "Temporary Address :",
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                TextMedium(text = profile?.response?.profile?.personal_information?.phone.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.email.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.marital_status.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.nationality.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.citizenship_no.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.permanent_address.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.temporary_address.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                            }
                                        }
                                    }

                                }

                                ProfileContentType.PaySlip.slug -> {
                                    Card(modifier = Modifier.fillMaxWidth()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .weight(0.7f)
                                                    .padding(end = 8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                TextMedium(
                                                    text = "Phone:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Email:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Marital Status:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Nationality:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Citizenship No.:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Permanent Address:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Temporary Address :",
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                TextMedium(text = profile?.response?.profile?.personal_information?.phone.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.email.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.marital_status.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.nationality.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.citizenship_no.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.permanent_address.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.temporary_address.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                            }
                                        }
                                    }

                                }

                                ProfileContentType.Approver.slug -> {
                                    Card(modifier = Modifier.fillMaxWidth()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .weight(0.7f)
                                                    .padding(end = 8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                TextMedium(
                                                    text = "Phone:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Email:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Marital Status:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Nationality:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Citizenship No.:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Permanent Address:",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                TextMedium(
                                                    text = "Temporary Address :",
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(start = 8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                TextMedium(text = profile?.response?.profile?.personal_information?.phone.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.email.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.marital_status.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.nationality.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.citizenship_no.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.permanent_address.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                                TextMedium(text = profile?.response?.profile?.personal_information?.temporary_address.takeIf { !it.isNullOrEmpty() }
                                                    ?: "N/A")
                                            }
                                        }
                                    }

                                }

                                else -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        TextMedium(
                                            text = content,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }*/
            } else {
                if (showEmpty) {
                    ShowEmpty()
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

    LaunchedEffect(profileState) {
        when (profileState) {
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
                showEmpty = false

                profile = (profileState as Result.Success<Profile>).data

                documentList = profile?.response?.documents as MutableList<Document>
                documentData = documentList

                trainingList = profile?.response?.training as MutableList<Training>
                trainingData = trainingList

                experienceList = profile?.response?.profile?.experience as MutableList<Experience>
                experienceData = experienceList

                if (profile != null) {
                    showEmpty = true
                }
            }

            is Result.Error -> {
                showLoading = false
                showSuccess = false
                showError = true
                showEmpty = false
                errorStatusCode = (profileState as Result.Error).error.statusCode
                errorTitle = ""
                errorMessage = (profileState as Result.Error).error.message
                errorType = ResponseType.None
            }
        }
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
}

private fun profile(profileViewModel: ProfileViewModel) {
    profileViewModel.getProfile()
}

@Composable
fun DocumentItem(documentData: Document) {
    Card(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(
                            text = "Phone",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Email",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Marital Status",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Nationality",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Citizenship No.",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Permanent Address",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Temporary Address ",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(text = documentData.name.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                        TextComponent(text = documentData.path?.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                    }
                }
            }
        }
    }

}

@Composable
fun TrainingItem(trainingData: Training) {

    Card(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(
                            text = "Name",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Course",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Time",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(text = trainingData.name.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                        TextComponent(text = trainingData.course.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                        TextComponent(text = trainingData.time.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                    }
                }
            }
        }
    }

}

@Composable
fun ExperienceData(modifier: Modifier, experienceData: Experience) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        TextComponent(text = experienceData.name.takeIf { !it.isNullOrEmpty() }
            ?: "N/A",
            maxLines = 2)
        TextComponent(text = experienceData.time.takeIf { !it.isNullOrEmpty() }
            ?: "N/A")
    }
}

@Composable
fun EducationData(modifier: Modifier, educationInformation: EducationInformation) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        TextComponent(text = educationInformation.name.takeIf { !it.isNullOrEmpty() }
            ?: "N/A")
        TextComponent(text = educationInformation.time.takeIf { !it.isNullOrEmpty() }
            ?: "N/A")
    }
}

@Composable
fun BiometricInformationItem(biometricInformation: BiometricInformation) {
    Card(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                            text = "Branch",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Device",
                            fontWeight = FontWeight.Bold
                        )
                        TextComponent(
                            text = "Updated At",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(text = biometricInformation.branch.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                        TextComponent(text = biometricInformation.device.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                        TextComponent(text = biometricInformation.updated_at.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A")
                    }
                }
            }
        }
    }
}