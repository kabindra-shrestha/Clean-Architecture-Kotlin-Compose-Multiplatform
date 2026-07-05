package com.kabindra.clean.architecture.presentation.ui.screen.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.kabindra.clean.architecture.presentation.ui.component.AppBrandIcon
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.AppIconFilled
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.ButtonAction
import com.kabindra.clean.architecture.presentation.ui.component.ButtonBack
import com.kabindra.clean.architecture.presentation.ui.component.ButtonClose
import com.kabindra.clean.architecture.presentation.ui.component.ButtonIcon
import com.kabindra.clean.architecture.presentation.ui.component.ButtonIconAndText
import com.kabindra.clean.architecture.presentation.ui.component.ButtonText
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.CardComponent
import com.kabindra.clean.architecture.presentation.ui.component.CardVariant
import com.kabindra.clean.architecture.presentation.ui.component.CheckboxCustom
import com.kabindra.clean.architecture.presentation.ui.component.DropdownField
import com.kabindra.clean.architecture.presentation.ui.component.ExpandableFabComponent
import com.kabindra.clean.architecture.presentation.ui.component.ExpressiveCarouselVariant
import com.kabindra.clean.architecture.presentation.ui.component.ExpressiveImageCarousel
import com.kabindra.clean.architecture.presentation.ui.component.ExpressiveTextCarousel
import com.kabindra.clean.architecture.presentation.ui.component.FloatingToolbarAction
import com.kabindra.clean.architecture.presentation.ui.component.FloatingToolbarComponent
import com.kabindra.clean.architecture.presentation.ui.component.FloatingToolbarVariant
import com.kabindra.clean.architecture.presentation.ui.component.HorizontalPagersWithTabs
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LoadingDialog
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.component.PasswordField
import com.kabindra.clean.architecture.presentation.ui.component.TabIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.TextType
import com.kabindra.clean.architecture.presentation.ui.component.TopAppBarComponent
import com.kabindra.clean.architecture.presentation.ui.component.ohteepee.OhTeePeeInput
import com.kabindra.clean.architecture.presentation.ui.component.ohteepee.configuration.OhTeePeeCellConfiguration
import com.kabindra.clean.architecture.presentation.ui.component.ohteepee.configuration.OhTeePeeConfigurations
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp

private enum class DashboardCategory(val title: String, val icon: ImageVector) {
    Brand("Brand", Icons.Default.Home),
    Typography("Typography", Icons.Default.Settings),
    Buttons("Buttons", Icons.Default.Add),
    Inputs("Inputs", Icons.Default.Lock),
    Cards("Cards", Icons.Default.Favorite),
    Loading("Loading", Icons.Default.Settings),
    Navigation("Navigation", Icons.Default.Person),
    Pager("Pager", Icons.Default.Home),
    Lists("Lists", Icons.Default.Person),
    Carousels("Carousels", Icons.Default.Favorite)
}

@Composable
fun DashboardScreen(
    innerPadding: PaddingValues,
    onNavigateLogin: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()

    var useExpressive by rememberSaveable { mutableStateOf(true) }
    var showLoadingDialog by rememberSaveable { mutableStateOf(false) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val categories = remember { DashboardCategory.entries }
    var selectedCategory by rememberSaveable { mutableStateOf(DashboardCategory.Brand) }
    val pagerState = rememberPagerState(pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

    val adaptiveInfo = currentWindowAdaptiveInfoV2()
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)

    val navSuiteState = rememberNavigationSuiteScaffoldState(
        initialValue = NavigationSuiteScaffoldValue.Hidden
    )

    LaunchedEffect(layoutType) {
        if (layoutType == NavigationSuiteType.NavigationBar) {
            navSuiteState.hide()   // mobile: rely on your custom FloatingToolbarComponent bottomBar
        } else {
            navSuiteState.show()   // web/tablet: show the rail/drawer
        }
    }

    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = -1,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            onDismiss = {},
        )
        return
    }

    if (showLoadingDialog) {
        LoadingDialog(
            isVisible = true,
            message = "Loading components...",
            useExpressive = useExpressive,
        )
        LaunchedEffect(Unit) {
            delay(3000)
            showLoadingDialog = false
        }
    }

    ModalBottomSheetComponent(
        title = "ModalBottomSheetComponent",
        text = "Showcasing configuration",
        isVisible = showBottomSheet,
        useExpressive = useExpressive,
        onDismiss = { showBottomSheet = false },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.sdp),
            verticalArrangement = Arrangement.spacedBy(10.sdp),
        ) {
            TextComponent(
                text = "Expressive Mode: ${if (useExpressive) "ON" else "OFF"}.",
                type = TextType.Title,
                size = TextSize.Medium,
            )
            ButtonText(
                modifier = Modifier.fillMaxWidth(),
                text = "Dismiss",
                useExpressiveShapes = useExpressive,
                onClick = { showBottomSheet = false },
            )
        }
    }

    NavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(
                WindowInsets(0, 0, 0, 0)
                    .union(WindowInsets.statusBars)
                    .union(WindowInsets.navigationBars)
            ),
        navigationSuiteItems = {
            if (layoutType != NavigationSuiteType.NavigationBar) {
                categories.forEach { category ->
                    item(
                        icon = { Icon(category.icon, contentDescription = category.title) },
                        label = { TextComponent(text = category.title, size = TextSize.Small) },
                        selected = selectedCategory == category,
                        onClick = {
                            selectedCategory = category
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(category.ordinal)
                            }
                        }
                    )
                }
            }
        },
        layoutType = layoutType,
        containerColor = MaterialTheme.colorScheme.background,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = Color.Transparent,
            navigationRailContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
        state = navSuiteState,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (layoutType == NavigationSuiteType.NavigationBar) {
                    val toolbarActions = remember(categories) {
                        categories.map { category ->
                            FloatingToolbarAction(
                                id = category.name,
                                label = category.title,
                                icon = category.icon
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingToolbarComponent(
                            modifier = Modifier.padding(horizontal = 16.sdp),
                            items = toolbarActions,
                            variant = FloatingToolbarVariant.HORIZONTAL,
                            showLabelBelowIcon = true,
                            useVibrantColors = useExpressive,
                            isScrollable = true,
                            onItemClick = { action ->
                                val index = categories.indexOfFirst { it.name == action.id }
                                if (index != -1) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        ) { scaffoldPadding ->
            val paddingToUse =
                if (layoutType == NavigationSuiteType.NavigationBar) scaffoldPadding else innerPadding
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingToUse.calculateBottomPadding())
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                // Sticky Header with Global Controls
                CardComponent(
                    modifier = Modifier.fillMaxWidth().padding(10.sdp),
                    variant = CardVariant.ELEVATED
                ) {
                    Column(
                        modifier = Modifier.padding(12.sdp),
                        verticalArrangement = Arrangement.spacedBy(10.sdp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.sdp)
                        ) {
                            AppIcon(modifier = Modifier.size(36.sdp))
                            Column(modifier = Modifier.weight(1f)) {
                                TextComponent(
                                    text = "Component Gallery",
                                    type = TextType.Title,
                                    size = TextSize.Large,
                                    fontWeight = FontWeight.Bold,
                                )
                                TextComponent(
                                    text = "Adaptive Expressive Showcase",
                                    type = TextType.Label,
                                    size = TextSize.Small,
                                )
                            }
                            ButtonText(
                                text = "Login Screen",
                                useExpressiveShapes = useExpressive,
                                onClick = onNavigateLogin
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.sdp)
                        ) {
                            ButtonText(
                                modifier = Modifier.weight(1f),
                                text = if (useExpressive) "Expressive: ON" else "Expressive: OFF",
                                isOutlined = true,
                                useExpressiveShapes = useExpressive,
                                onClick = { useExpressive = !useExpressive }
                            )
                            ButtonIconAndText(
                                modifier = Modifier.weight(1f),
                                text = "Global Loader",
                                useExpressiveShapes = useExpressive,
                                onClick = { showLoadingDialog = true }
                            )
                        }
                    }
                }

                // Sync selectedCategory when swiping
                LaunchedEffect(pagerState.currentPage) {
                    selectedCategory = categories[pagerState.currentPage]
                }

                // Pager Content
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top,
                    userScrollEnabled = true
                ) { page ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus()
                                })
                            }
                            .padding(12.sdp),
                        verticalArrangement = Arrangement.spacedBy(15.sdp)
                    ) {
                        when (categories[page]) {
                            DashboardCategory.Brand -> BrandShowcase()
                            DashboardCategory.Typography -> TypographyShowcase()
                            DashboardCategory.Buttons -> ButtonsShowcase(
                                useExpressive,
                                onOpenSheet = { showBottomSheet = true })

                            DashboardCategory.Inputs -> InputsShowcase(useExpressive)
                            DashboardCategory.Cards -> CardsShowcase(useExpressive)
                            DashboardCategory.Loading -> LoadingShowcase(
                                useExpressive,
                                onTriggerLoading = { showLoadingDialog = true })

                            DashboardCategory.Navigation -> NavigationShowcase(useExpressive)
                            DashboardCategory.Pager -> PagerShowcase(useExpressive)
                            DashboardCategory.Lists -> ListsShowcase(useExpressive)
                            DashboardCategory.Carousels -> CarouselsShowcase(useExpressive)
                        }

                        Spacer(modifier = Modifier.height(8.sdp))
                    }
                }
            }
        }
    }
}

@Composable
private fun BrandShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Brand Assets")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.sdp)
            ) {
                LabelText("AppIconFilled")
                AppIconFilled(modifier = Modifier.width(200.sdp).height(75.sdp))

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.sdp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.sdp)
                    ) {
                        LabelText("AppBrandIcon")
                        AppBrandIcon(modifier = Modifier.size(64.sdp))
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.sdp)
                    ) {
                        LabelText("AppIcon")
                        AppIcon(modifier = Modifier.size(64.sdp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TypographyShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Typography System")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                val types = listOf(
                    TextType.Display,
                    TextType.Headline,
                    TextType.Title,
                    TextType.Body,
                    TextType.Label
                )
                val sizes = listOf(TextSize.Large, TextSize.Medium, TextSize.Small)

                types.forEach { type ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.sdp)) {
                        TextComponent(
                            text = type.name,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            size = TextSize.Small
                        )
                        sizes.forEach { size ->
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(8.sdp)
                            ) {
                                LabelText(size.name)
                                TextComponent(
                                    text = "${type.name} ${size.name}",
                                    type = type,
                                    size = size
                                )
                            }
                        }
                    }
                    if (type != types.last()) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Composable
private fun ButtonsShowcase(useExpressive: Boolean, onOpenSheet: () -> Unit) {
    val fabActions = remember {
        listOf(
            Pair("Create Task", Icons.Default.Add),
            Pair("App Settings", Icons.Default.Settings),
            Pair("Save Favorite", Icons.Default.Favorite)
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Button Variants")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                LabelText("ButtonText (Primary vs Outlined)")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    ButtonText(
                        text = "Primary Action",
                        modifier = Modifier.weight(1f),
                        useExpressiveShapes = useExpressive,
                        onClick = {})
                    ButtonText(
                        text = "Secondary Action",
                        modifier = Modifier.weight(1f),
                        isOutlined = true,
                        useExpressiveShapes = useExpressive,
                        onClick = {})
                }

                LabelText("ButtonIconAndText (Start vs End Icon)")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    ButtonIconAndText(
                        text = "Icon Start",
                        modifier = Modifier.weight(1f),
                        useExpressiveShapes = useExpressive,
                        onClick = {})
                    ButtonTextAndIcon(
                        text = "Icon End",
                        modifier = Modifier.weight(1f),
                        useExpressiveShapes = useExpressive,
                        onClick = {})
                }
            }
        }

        SectionTitle("Navigation & Quick Actions")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(12.sdp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("ButtonIcon")
                    ButtonIcon(useExpressiveShapes = useExpressive, onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("ButtonBack")
                    ButtonBack(useExpressiveShapes = useExpressive, onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("ButtonClose")
                    ButtonClose(useExpressiveShapes = useExpressive, onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("ButtonAction")
                    ButtonAction(useExpressiveShapes = useExpressive, onClick = {})
                }
            }
        }

        SectionTitle("Expressive Expandable FAB")
        CardComponent(
            modifier = Modifier.fillMaxWidth().height(200.sdp),
            variant = CardVariant.FILLED
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                TextComponent(
                    modifier = Modifier.padding(12.sdp),
                    text = "ExpandableFabComponent Demo",
                    size = TextSize.Small,
                    fontWeight = FontWeight.SemiBold
                )
                ExpandableFabComponent(
                    items = fabActions,
                    itemTitle = { it.first },
                    itemIcon = { it.second },
                    onItemClick = {},
                    useExpressive = useExpressive
                )
            }
        }

        SectionTitle("Modal Components")
        ButtonText(
            text = "Launch ModalBottomSheetComponent",
            modifier = Modifier.fillMaxWidth(),
            isOutlined = true,
            useExpressiveShapes = useExpressive,
            onClick = onOpenSheet
        )
    }
}

@Composable
private fun InputsShowcase(useExpressive: Boolean) {
    var text by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var otpValue by rememberSaveable { mutableStateOf("") }
    var selectedRole by rememberSaveable { mutableStateOf<String?>(null) }
    var checked by rememberSaveable { mutableStateOf(false) }
    val roles = remember { listOf("Administrator", "Editor", "Viewer") }

    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Text & Secure Inputs")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                LabelText("InputField")
                InputField(
                    value = text,
                    onValueChange = { text = it },
                    label = "Full Name",
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Default.Person,
                    trialingIcon = if (text.isNotEmpty()) Icons.Default.Close else null,
                    onClickTrailingIcon = { text = "" }
                )

                LabelText("PasswordField")
                PasswordField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Secure Password",
                    isError = password.isNotEmpty() && password.length < 6,
                    errorText = "Password must be at least 6 characters",
                    leadingIcon = Icons.Default.Lock,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        SectionTitle("OTP Verification")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                LabelText("OhTeePeeInput (6 Interactive Cells)")
                OhTeePeeInput(
                    value = otpValue,
                    onValueChange = { newValue, _ -> otpValue = newValue },
                    configurations = OhTeePeeConfigurations.withDefaults(
                        cellsCount = 6,
                        emptyCellConfig = OhTeePeeCellConfiguration.withDefaults(
                            shape = if (useExpressive) MaterialTheme.shapes.large else MaterialTheme.shapes.medium,
                            borderColor = MaterialTheme.colorScheme.outline
                        ),
                        activeCellConfig = OhTeePeeCellConfiguration.withDefaults(
                            shape = if (useExpressive) MaterialTheme.shapes.large else MaterialTheme.shapes.medium,
                            borderColor = MaterialTheme.colorScheme.primary,
                            borderWidth = 2.sdp
                        )
                    ),
                    modifier = Modifier.padding(vertical = 8.sdp)
                )
            }
        }

        SectionTitle("Selection Controls")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                LabelText("DropdownField")
                DropdownField(
                    items = roles,
                    itemContent = { it },
                    value = selectedRole ?: "Select User Role",
                    selectedItem = selectedRole,
                    onItemSelected = { selectedRole = it },
                    label = "User Role",
                    leadingIcon = Icons.Default.Settings,
                    isEnabled = true,
                    modifier = Modifier.fillMaxWidth()
                )

                LabelText("CheckboxCustom")
                CheckboxCustom(
                    isChecked = checked,
                    onCheckedChange = { checked = it },
                    useExpressiveStyle = useExpressive,
                    label = "I agree to the terms of service and privacy policy"
                )
            }
        }
    }
}

@Composable
private fun CardsShowcase(useExpressive: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Card Variant Showcase")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.sdp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                LabelText("CardVariant.ELEVATED")
                CardComponent(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.ELEVATED,
                    useExpressive = useExpressive
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextComponent(
                            text = "Elevated Surface",
                            type = TextType.Title,
                            size = TextSize.Medium
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                LabelText("CardVariant.FILLED")
                CardComponent(
                    modifier = Modifier.weight(1f),
                    variant = CardVariant.FILLED,
                    useExpressive = useExpressive
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextComponent(
                            text = "Filled Surface",
                            type = TextType.Title,
                            size = TextSize.Medium
                        )
                    }
                }
            }
        }

        LabelText("CardVariant.OUTLINED")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.OUTLINED,
            useExpressive = useExpressive
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(80.sdp),
                contentAlignment = Alignment.Center
            ) {
                TextComponent(
                    text = "Outlined Surface",
                    type = TextType.Title,
                    size = TextSize.Medium
                )
            }
        }

        SectionTitle("Adaptive Card Layouts")
        CardComponent(modifier = Modifier.fillMaxWidth(), useExpressive = useExpressive) {
            Column(
                modifier = Modifier.padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                TextComponent(
                    text = "Nested Content Section",
                    fontWeight = FontWeight.Bold,
                    size = TextSize.Medium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.sdp)
                ) {
                    CardComponent(
                        modifier = Modifier.weight(1f),
                        variant = CardVariant.FILLED,
                        useExpressive = useExpressive
                    ) {
                        TextComponent(
                            modifier = Modifier.padding(12.sdp),
                            text = "Child A",
                            size = TextSize.Small
                        )
                    }
                    CardComponent(
                        modifier = Modifier.weight(1f),
                        variant = CardVariant.OUTLINED,
                        useExpressive = useExpressive
                    ) {
                        TextComponent(
                            modifier = Modifier.padding(12.sdp),
                            text = "Child B",
                            size = TextSize.Small
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingShowcase(useExpressive: Boolean, onTriggerLoading: () -> Unit) {
    val progressTransition = rememberInfiniteTransition(label = "loading-progress")
    val progress by progressTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "progress",
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Wavy Progress Indicators")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(20.sdp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.sdp)) {
                    LabelText("ExpressiveLinearProgressIndicator (Determinate)")
                    LoadingIndicator(
                        isCircular = false,
                        progress = progress,
                        useExpressive = useExpressive
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.sdp)) {
                    LabelText("ExpressiveLinearProgressIndicator (Indeterminate)")
                    LoadingIndicator(isCircular = false, useExpressive = useExpressive)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.sdp)
                    ) {
                        LabelText("Circular Determinate")
                        LoadingIndicator(
                            modifier = Modifier.size(56.sdp),
                            isCircular = true,
                            progress = progress,
                            useExpressive = useExpressive
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.sdp)
                    ) {
                        LabelText("Circular Indeterminate")
                        LoadingIndicator(
                            modifier = Modifier.size(56.sdp),
                            isCircular = true,
                            useExpressive = useExpressive
                        )
                    }
                }
            }
        }

        SectionTitle("Global Overlays")
        ButtonText(
            text = "Trigger Global LoadingDialog",
            modifier = Modifier.fillMaxWidth(),
            isOutlined = true,
            useExpressiveShapes = useExpressive,
            onClick = onTriggerLoading
        )
    }
}

@Composable
private fun NavigationShowcase(useExpressive: Boolean) {
    val toolbarActions = remember {
        listOf(
            FloatingToolbarAction(id = "1", label = "Home", icon = Icons.Default.Home),
            FloatingToolbarAction(id = "2", label = "Favs", icon = Icons.Default.Favorite),
            FloatingToolbarAction(id = "3", label = "Profile", icon = Icons.Default.Person),
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("App & Navigation Bars")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.sdp)) {
                    LabelText("TopAppBarComponent")
                    TopAppBarComponent(
                        title = "Navigation Title",
                        subtitle = if (useExpressive) "Expressive subtitle enabled" else "",
                        canNavigateBack = true,
                        useExpressive = useExpressive,
                        onBackNavigate = {}
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.sdp)) {
                    LabelText("BottomNavigationBarComponent")
                    // Removed actual call to BottomNavigationBarComponent to fix compilation 
                    // since we moved to FloatingToolbar for the main navigation.
                    // We can still showcase it if needed by restoring the component.
                    CardComponent(modifier = Modifier.fillMaxWidth().height(50.sdp)) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TextComponent(
                                text = "BottomNavigationBarComponent Showcase Placeholder",
                                size = TextSize.Small
                            )
                        }
                    }
                }
            }
        }

        SectionTitle("Adaptive Floating Toolbars")

        CardComponent(modifier = Modifier.fillMaxWidth().padding(vertical = 4.sdp)) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.sdp)) {
                    LabelText("FloatingToolbarVariant.HORIZONTAL")
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingToolbarComponent(
                            items = toolbarActions,
                            variant = FloatingToolbarVariant.HORIZONTAL,
                            useVibrantColors = useExpressive
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.sdp)) {
                    LabelText("FloatingToolbarVariant.HORIZONTAL_WITH_FAB")
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingToolbarComponent(
                            items = toolbarActions,
                            variant = FloatingToolbarVariant.HORIZONTAL_WITH_FAB,
                            fabIcon = Icons.Default.Add,
                            useVibrantColors = useExpressive
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().height(180.sdp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.sdp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LabelText("VERTICAL")
                        FloatingToolbarComponent(
                            items = toolbarActions,
                            variant = FloatingToolbarVariant.VERTICAL,
                            useVibrantColors = useExpressive
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.sdp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LabelText("VERTICAL_WITH_FAB")
                        FloatingToolbarComponent(
                            items = toolbarActions,
                            variant = FloatingToolbarVariant.VERTICAL_WITH_FAB,
                            fabIcon = Icons.Default.Add,
                            useVibrantColors = useExpressive
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PagerShowcase(useExpressive: Boolean) {
    val items =
        remember { List(4) { index -> "Interactive Pager Content for Step ${index + 1}: This demonstrates how HorizontalPagersWithTabs provides a realistic navigation flow within a screen." } }
    val tabs = remember { listOf("Step One", "Step Two", "Step Three", "Step Four") }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("Interactive Pagers")

        LabelText("HorizontalPagersWithTabs (Realistic Navigation)")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.OUTLINED,
            useExpressive = useExpressive
        ) {
            HorizontalPagersWithTabs(
                tabs = tabs,
                pageItems = items,
                modifier = Modifier.fillMaxWidth().height(250.sdp),
                tabIndicator = { selectedIndex -> TabIndicator(selectedIndex) },
                pageContent = { _, _, item ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.sdp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CardComponent(
                            modifier = Modifier.fillMaxWidth(),
                            variant = CardVariant.ELEVATED,
                            useExpressive = useExpressive
                        ) {
                            Column(
                                modifier = Modifier.padding(20.sdp),
                                verticalArrangement = Arrangement.spacedBy(12.sdp)
                            ) {
                                TextComponent(
                                    text = item,
                                    textAlign = TextAlign.Center,
                                    maxLines = 5,
                                    size = TextSize.Medium
                                )
                                ButtonText(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = "Continue",
                                    useExpressiveShapes = useExpressive,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ListsShowcase(useExpressive: Boolean) {
    val items = remember { List(12) { "Gallery Item Reference #$it" } }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("Lists & Adaptive Grids")

        LabelText("BaseLazy (Vertical List Variant)")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.OUTLINED,
            useExpressive = useExpressive
        ) {
            BaseLazy(
                items = items,
                listType = LazyListType.LIST,
                useExpressiveLoadingIndicator = useExpressive,
                modifier = Modifier.fillMaxWidth().height(200.sdp),
                onLoadMore = {},
                onScrollStateChanged = {},
                itemContent = { index, item ->
                    CardComponent(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.sdp, vertical = 4.sdp),
                        variant = if (index % 2 == 0) CardVariant.ELEVATED else CardVariant.FILLED,
                        useExpressive = useExpressive
                    ) {
                        TextComponent(
                            text = item,
                            modifier = Modifier.padding(12.sdp),
                            size = TextSize.Small
                        )
                    }
                }
            )
        }

        LabelText("BaseLazy (2-Column Grid Variant)")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.OUTLINED,
            useExpressive = useExpressive
        ) {
            BaseLazy(
                items = items,
                listType = LazyListType.GRID,
                spanCount = 2,
                useExpressiveLoadingIndicator = useExpressive,
                modifier = Modifier.fillMaxWidth().height(250.sdp),
                onLoadMore = {},
                onScrollStateChanged = {},
                itemContent = { _, item ->
                    CardComponent(
                        modifier = Modifier.padding(6.sdp).fillMaxWidth(),
                        variant = CardVariant.ELEVATED,
                        useExpressive = useExpressive
                    ) {
                        Column(
                            modifier = Modifier.padding(12.sdp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.sdp)
                        ) {
                            AppIcon(modifier = Modifier.size(32.sdp))
                            TextComponent(
                                text = item,
                                size = TextSize.Small,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun CarouselsShowcase(useExpressive: Boolean) {
    val textItems = remember {
        listOf(
            "Dynamic Slide One",
            "Elegant Page Two",
            "Modern View Three",
            "Smooth Motion Four"
        )
    }
    val imageUrls = remember {
        listOf(
            "https://picsum.photos/id/10/400/300",
            "https://picsum.photos/id/20/400/300",
            "https://picsum.photos/id/30/400/300",
            "https://picsum.photos/id/40/400/300",
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("Carousel Display Styles")

        LabelText("ExpressiveCarouselVariant.MULTI_BROWSE")
        ExpressiveTextCarousel(
            items = textItems,
            variant = ExpressiveCarouselVariant.MULTI_BROWSE,
            modifier = Modifier.fillMaxWidth().height(130.sdp)
        )

        LabelText("ExpressiveCarouselVariant.CENTERED_HERO")
        ExpressiveImageCarousel(
            imageUrls = imageUrls,
            variant = ExpressiveCarouselVariant.CENTERED_HERO,
            modifier = Modifier.fillMaxWidth().height(180.sdp)
        )

        LabelText("ExpressiveCarouselVariant.UNCONTAINED")
        ExpressiveTextCarousel(
            items = textItems,
            variant = ExpressiveCarouselVariant.UNCONTAINED,
            modifier = Modifier.fillMaxWidth().height(130.sdp)
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    TextComponent(
        text = title,
        type = TextType.Headline,
        size = TextSize.Small,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 10.sdp, bottom = 4.sdp)
    )
}

@Composable
private fun LabelText(text: String) {
    TextComponent(
        text = text,
        type = TextType.Label,
        size = TextSize.Small,
        fontWeight = FontWeight.Medium,
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 4.sdp)
    )
}
