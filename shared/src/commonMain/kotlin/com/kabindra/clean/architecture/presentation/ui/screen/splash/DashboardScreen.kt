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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.kabindra.clean.architecture.presentation.ui.component.AppBrandIcon
import com.kabindra.clean.architecture.presentation.ui.component.AppIcon
import com.kabindra.clean.architecture.presentation.ui.component.AppIconFilled
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.BottomNavigationBarComponent
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

private enum class DashboardCategory(val title: String) {
    Brand("Brand"),
    Typography("Typography"),
    Buttons("Buttons"),
    Inputs("Inputs"),
    Cards("Cards"),
    Loading("Loading"),
    Navigation("Navigation"),
    Pager("Pager"),
    Lists("Lists"),
    Carousels("Carousels")
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
    val pagerState = rememberPagerState(pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

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
        text = "Showcasing current configuration",
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
                text = "Expressive Mode is currently ${if (useExpressive) "ON" else "OFF"}.",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
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
                            text = "Interactive Showcase",
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

        // Categories Tabs
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 12.sdp,
            divider = {},
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    )
                }
            }
        ) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        TextComponent(
                            text = category.title,
                            fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                            size = TextSize.Small
                        )
                    }
                )
            }
        }

        // Pager Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
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

                Spacer(modifier = Modifier.height(20.sdp))
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
                verticalArrangement = Arrangement.spacedBy(15.sdp)
            ) {
                LabelText("AppIconFilled")
                AppIconFilled(modifier = Modifier.width(120.sdp).height(45.sdp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LabelText("AppBrandIcon")
                        AppBrandIcon(modifier = Modifier.size(48.sdp))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LabelText("AppIcon")
                        AppIcon(modifier = Modifier.size(48.sdp))
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
                verticalArrangement = Arrangement.spacedBy(12.sdp)
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
                    sizes.forEach { size ->
                        Column {
                            LabelText("${type.name} ${size.name}")
                            TextComponent(
                                text = "${type.name} ${size.name}",
                                type = type,
                                size = size
                            )
                        }
                    }
                    if (type != types.last()) Spacer(modifier = Modifier.height(4.sdp))
                }
            }
        }
    }
}

@Composable
private fun ButtonsShowcase(useExpressive: Boolean, onOpenSheet: () -> Unit) {
    val fabActions = remember {
        listOf(
            Pair("Add Item", Icons.Default.Add),
            Pair("Settings", Icons.Default.Settings),
            Pair("Favorites", Icons.Default.Favorite)
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Standard Buttons")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                LabelText("ButtonText (Primary)")
                ButtonText(
                    text = "Primary Action",
                    modifier = Modifier.fillMaxWidth(),
                    useExpressiveShapes = useExpressive,
                    onClick = {})

                LabelText("ButtonText (Outlined)")
                ButtonText(
                    text = "Secondary Action",
                    modifier = Modifier.fillMaxWidth(),
                    isOutlined = true,
                    useExpressiveShapes = useExpressive,
                    onClick = {})

                LabelText("ButtonIconAndText")
                ButtonIconAndText(
                    text = "Icon Start",
                    modifier = Modifier.fillMaxWidth(),
                    useExpressiveShapes = useExpressive,
                    onClick = {})

                LabelText("ButtonTextAndIcon")
                ButtonTextAndIcon(
                    text = "Icon End",
                    modifier = Modifier.fillMaxWidth(),
                    useExpressiveShapes = useExpressive,
                    onClick = {})

                LabelText("Open Bottom Sheet (Outlined)")
                ButtonText(
                    text = "Launch Sheet",
                    modifier = Modifier.fillMaxWidth(),
                    isOutlined = true,
                    useExpressiveShapes = useExpressive,
                    onClick = onOpenSheet
                )
            }
        }

        SectionTitle("Action & Navigation Icons")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(12.sdp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("Icon")
                    ButtonIcon(useExpressiveShapes = useExpressive, onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("Back")
                    ButtonBack(useExpressiveShapes = useExpressive, onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("Close")
                    ButtonClose(useExpressiveShapes = useExpressive, onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LabelText("Action")
                    ButtonAction(useExpressiveShapes = useExpressive, onClick = {})
                }
            }
        }

        SectionTitle("Floating Action Button")
        LabelText("ExpandableFabComponent")
        Box(modifier = Modifier.fillMaxWidth().height(180.sdp)) {
            ExpandableFabComponent(
                items = fabActions,
                itemTitle = { it.first },
                itemIcon = { it.second },
                onItemClick = {},
                useExpressive = useExpressive
            )
        }
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
        SectionTitle("Text Inputs")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp)
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
                    errorText = "Password too short",
                    leadingIcon = Icons.Default.Lock,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        SectionTitle("Verification (OTP)")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabelText("OhTeePeeInput (6 Cells)")
                OhTeePeeInput(
                    value = otpValue,
                    onValueChange = { newValue, _ -> otpValue = newValue },
                    configurations = OhTeePeeConfigurations.withDefaults(
                        cellsCount = 6,
                        emptyCellConfig = OhTeePeeCellConfiguration.withDefaults(
                            shape = if (useExpressive) MaterialTheme.shapes.large else MaterialTheme.shapes.medium,
                            borderColor = Color.Gray
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

        SectionTitle("Selection & Options")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                LabelText("DropdownField")
                DropdownField(
                    items = roles,
                    itemContent = { it },
                    value = selectedRole ?: "Select Role",
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
                    label = "Agree to the terms of service"
                )
            }
        }
    }
}

@Composable
private fun CardsShowcase(useExpressive: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(12.sdp)) {
        SectionTitle("Material 3 Card Variants")

        LabelText("CardVariant.ELEVATED")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.ELEVATED,
            useExpressive = useExpressive
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(80.sdp),
                contentAlignment = Alignment.Center
            ) {
                TextComponent(
                    text = "Elevated Surface",
                    type = TextType.Title,
                    size = TextSize.Medium
                )
            }
        }

        LabelText("CardVariant.FILLED")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.FILLED,
            useExpressive = useExpressive
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(80.sdp),
                contentAlignment = Alignment.Center
            ) {
                TextComponent(
                    text = "Filled Surface",
                    type = TextType.Title,
                    size = TextSize.Medium
                )
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

        SectionTitle("Creative Layouts")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            CardComponent(
                modifier = Modifier.weight(1f),
                variant = CardVariant.ELEVATED,
                useExpressive = useExpressive
            ) {
                Column(modifier = Modifier.padding(12.sdp)) {
                    TextComponent(text = "Column A", fontWeight = FontWeight.Bold)
                    TextComponent(
                        text = "Nested content inside an elevated card.",
                        size = TextSize.Small,
                        maxLines = 3
                    )
                }
            }
            CardComponent(
                modifier = Modifier.weight(1f),
                variant = CardVariant.OUTLINED,
                useExpressive = useExpressive
            ) {
                Column(modifier = Modifier.padding(12.sdp)) {
                    TextComponent(text = "Column B", fontWeight = FontWeight.Bold)
                    TextComponent(
                        text = "Bordered variation for distinct grouping.",
                        size = TextSize.Small,
                        maxLines = 3
                    )
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
        SectionTitle("Progress Indicators")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(15.sdp)
            ) {
                LabelText("Linear Progress (Wavy/Determinate)")
                LoadingIndicator(
                    isCircular = false,
                    progress = progress,
                    useExpressive = useExpressive
                )

                LabelText("Linear Progress (Indeterminate)")
                LoadingIndicator(isCircular = false, useExpressive = useExpressive)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.sdp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LabelText("Circular (Determinate)")
                        LoadingIndicator(
                            modifier = Modifier.size(48.sdp),
                            isCircular = true,
                            progress = progress,
                            useExpressive = useExpressive
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LabelText("Indeterminate")
                        LoadingIndicator(
                            modifier = Modifier.size(48.sdp),
                            isCircular = true,
                            useExpressive = useExpressive
                        )
                    }
                }
            }
        }

        SectionTitle("Dialog Overlays")
        ButtonText(
            text = "Trigger LoadingDialog",
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
        SectionTitle("Standard App Bars")
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(12.sdp),
                verticalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                LabelText("TopAppBarComponent")
                TopAppBarComponent(
                    title = "Page Title",
                    subtitle = if (useExpressive) "Expressive subtitle" else "",
                    canNavigateBack = true,
                    useExpressive = useExpressive,
                    onBackNavigate = {}
                )

                LabelText("BottomNavigationBarComponent")
                BottomNavigationBarComponent(
                    selectedRoute = "home",
                    onClick = {}
                )
            }
        }

        SectionTitle("Floating Toolbars")

        LabelText("Variant: HORIZONTAL")
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

        LabelText("Variant: HORIZONTAL_WITH_FAB")
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

        LabelText("Variant: VERTICAL")
        Box(
            modifier = Modifier.fillMaxWidth().height(150.sdp),
            contentAlignment = Alignment.CenterEnd
        ) {
            FloatingToolbarComponent(
                items = toolbarActions,
                variant = FloatingToolbarVariant.VERTICAL,
                useVibrantColors = useExpressive
            )
        }

        LabelText("Variant: VERTICAL_WITH_FAB")
        Box(
            modifier = Modifier.fillMaxWidth().height(180.sdp),
            contentAlignment = Alignment.CenterEnd
        ) {
            FloatingToolbarComponent(
                items = toolbarActions,
                variant = FloatingToolbarVariant.VERTICAL_WITH_FAB,
                fabIcon = Icons.Default.Add,
                useVibrantColors = useExpressive
            )
        }
    }
}

@Composable
private fun PagerShowcase(useExpressive: Boolean) {
    val items =
        remember { List(4) { index -> "Pager Content for Tab ${index + 1}: Detailed description of the feature or component shown in this specific horizontal view." } }
    val tabs = remember { listOf("Overview", "Features", "Analytics", "Settings") }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("Horizontal Pager Showcase")

        LabelText("HorizontalPagersWithTabs (Interactive)")
        CardComponent(
            modifier = Modifier.fillMaxWidth(),
            variant = CardVariant.OUTLINED,
            useExpressive = useExpressive
        ) {
            HorizontalPagersWithTabs(
                tabs = tabs,
                pageItems = items,
                modifier = Modifier.fillMaxWidth().height(220.sdp),
                tabIndicator = { selectedIndex -> TabIndicator(selectedIndex) },
                pageContent = { _, _, item ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(15.sdp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CardComponent(
                            modifier = Modifier.fillMaxWidth(),
                            variant = CardVariant.FILLED,
                            useExpressive = useExpressive
                        ) {
                            Column(modifier = Modifier.padding(15.sdp)) {
                                TextComponent(
                                    text = item,
                                    textAlign = TextAlign.Center,
                                    maxLines = 5
                                )
                                Spacer(modifier = Modifier.height(10.sdp))
                                ButtonText(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = "Action",
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
    val items = remember { List(12) { "List/Grid Item #$it" } }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("Lists & Grids")

        LabelText("BaseLazy (Standard Vertical List)")
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
                        TextComponent(text = item, modifier = Modifier.padding(12.sdp))
                    }
                }
            )
        }

        LabelText("BaseLazy (2-Column Adaptive Grid)")
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
                        modifier = Modifier.padding(5.sdp).fillMaxWidth(),
                        variant = CardVariant.ELEVATED,
                        useExpressive = useExpressive
                    ) {
                        Column(
                            modifier = Modifier.padding(10.sdp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppIcon(modifier = Modifier.size(24.sdp))
                            Spacer(modifier = Modifier.height(5.sdp))
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
            "First Dynamic Slide",
            "Second Elegant Page",
            "Third Modern View",
            "Fourth Smooth Motion"
        )
    }
    val imageUrls = remember {
        listOf(
            "https://picsum.photos/id/237/400/300",
            "https://picsum.photos/id/238/400/300",
            "https://picsum.photos/id/239/400/300",
            "https://picsum.photos/id/240/400/300",
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(15.sdp)) {
        SectionTitle("Carousel Styles")

        LabelText("MULTI_BROWSE")
        ExpressiveTextCarousel(
            items = textItems,
            variant = ExpressiveCarouselVariant.MULTI_BROWSE,
            modifier = Modifier.fillMaxWidth().height(120.sdp)
        )

        LabelText("CENTERED_HERO")
        ExpressiveImageCarousel(
            imageUrls = imageUrls,
            variant = ExpressiveCarouselVariant.CENTERED_HERO,
            modifier = Modifier.fillMaxWidth().height(160.sdp)
        )

        LabelText("UNCONTAINED")
        ExpressiveTextCarousel(
            items = textItems,
            variant = ExpressiveCarouselVariant.UNCONTAINED,
            modifier = Modifier.fillMaxWidth().height(120.sdp)
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
        modifier = Modifier.padding(top = 10.sdp, bottom = 2.sdp)
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
