package com.kabindra.clean.architecture.presentation.ui.screen.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.kabindra.clean.architecture.presentation.ui.component.HorizontalPagersWithTabs
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.LoadingDialog
import com.kabindra.clean.architecture.presentation.ui.component.LoadingIndicator
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.component.PasswordField
import com.kabindra.clean.architecture.presentation.ui.component.TabIndicator
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.TextType
import com.kabindra.clean.architecture.presentation.ui.component.TopAppBarComponent
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp

private data class DashboardFabAction(
    val title: String,
    val icon: ImageVector
)

@Composable
fun DashboardScreen(
    innerPadding: PaddingValues,
    onNavigateLogin: () -> Unit,
) {
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()

    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var selectedRole by rememberSaveable { mutableStateOf<String?>(null) }
    var isPolicyAccepted by rememberSaveable { mutableStateOf(false) }
    var showLoadingDialog by rememberSaveable { mutableStateOf(false) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var useExpressive by rememberSaveable { mutableStateOf(true) }
    var lazyHasScrolled by rememberSaveable { mutableStateOf(false) }

    val pagerTabs = remember { listOf("Buttons", "Inputs", "Progress") }
    val pagerItems = remember {
        listOf(
            "Shared button wrappers for consistent actions.",
            "Shared field wrappers for consistent forms.",
            "Shared expressive wave indicators for all loading states.",
        )
    }
    val lazyItems = remember { List(8) { "Reusable list item #${it + 1}" } }
    val carouselItems = remember {
        listOf(
            "Active Users",
            "Engagement",
            "Conversion",
            "Performance",
            "Revenue",
            "Retention",
        )
    }
    val carouselImageUrls = remember {
        listOf(
            "https://picsum.photos/id/1015/800/600",
            "https://picsum.photos/id/1016/800/600",
            "https://picsum.photos/id/1025/800/600",
            "https://picsum.photos/id/1035/800/600",
            "https://picsum.photos/id/1043/800/600",
            "https://picsum.photos/id/1050/800/600",
        )
    }
    val roleOptions = remember { listOf("Admin", "Manager", "Viewer") }
    val fabActions = remember {
        listOf(
            DashboardFabAction("Create", Icons.Default.Add),
            DashboardFabAction("Favorite", Icons.Default.Favorite),
            DashboardFabAction("Settings", Icons.Default.Settings),
        )
    }

    val progressTransition = rememberInfiniteTransition(label = "dashboard-progress")
    val progress by progressTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dashboard-progress-value",
    )

    DisposableEffect(Unit) { onDispose { } }

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
            message = "Loading reusable components preview..",
            useExpressive = useExpressive,
        )
        LaunchedEffect(Unit) {
            delay(1200)
            showLoadingDialog = false
        }
    }

    ModalBottomSheetComponent(
        title = "Reusable Bottom Sheet",
        text = "Close bottom sheet",
        isVisible = showBottomSheet,
        useExpressive = useExpressive,
        onDismiss = { showBottomSheet = false },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp),
        ) {
            TextComponent(
                text = "This sheet is rendered with `ModalBottomSheetComponent`.",
                maxLines = 3,
            )
            ButtonText(
                modifier = Modifier.fillMaxWidth(),
                text = "Close",
                onClick = { showBottomSheet = false },
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 16.sdp,
                vertical = 12.sdp
            ),
        verticalArrangement = Arrangement.spacedBy(16.sdp),
    ) {
        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                AppIcon(
                    modifier = Modifier
                        .width(72.sdp)
                        .height(72.sdp),
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.sdp),
                ) {
                    TextComponent(
                        text = "Reusable Components Dashboard",
                        type = TextType.Title,
                        size = TextSize.Large,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                    )
                    TextComponent(
                        text = "Live demo of shared UI blocks used across screens.",
                        size = TextSize.Medium,
                        maxLines = 2,
                    )
                }
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(10.sdp),
            ) {
                TextComponent(
                    text = "Text + App Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIconFilled(
                        modifier = Modifier
                            .width(120.sdp)
                            .height(46.sdp)
                    )
                    AppBrandIcon(modifier = Modifier.size(36.sdp))
                }
                TextComponent(
                    text = "TextComponent (Display/Headline/Title/Body/Label)",
                    type = TextType.Label,
                    size = TextSize.Small,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                )
                TextComponent(
                    text = "Display Large",
                    type = TextType.Display,
                    size = TextSize.Large
                )
                TextComponent(
                    text = "Display Medium",
                    type = TextType.Display,
                    size = TextSize.Medium
                )
                TextComponent(
                    text = "Display Small",
                    type = TextType.Display,
                    size = TextSize.Small
                )
                TextComponent(
                    text = "Headline Large",
                    type = TextType.Headline,
                    size = TextSize.Large
                )
                TextComponent(
                    text = "Headline Medium",
                    type = TextType.Headline,
                    size = TextSize.Medium
                )
                TextComponent(
                    text = "Headline Small",
                    type = TextType.Headline,
                    size = TextSize.Small
                )
                TextComponent(text = "Title Large", type = TextType.Title, size = TextSize.Large)
                TextComponent(text = "Title Medium", type = TextType.Title, size = TextSize.Medium)
                TextComponent(text = "Title Small", type = TextType.Title, size = TextSize.Small)
                TextComponent(text = "Body Large", type = TextType.Body, size = TextSize.Large)
                TextComponent(text = "Body Medium", type = TextType.Body, size = TextSize.Medium)
                TextComponent(text = "Body Small", type = TextType.Body, size = TextSize.Small)
                TextComponent(text = "Label Large", type = TextType.Label, size = TextSize.Large)
                TextComponent(text = "Label Medium", type = TextType.Label, size = TextSize.Medium)
                TextComponent(text = "Label Small", type = TextType.Label, size = TextSize.Small)
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(8.sdp),
            ) {
                TextComponent(
                    text = "Scaffold Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                TopAppBarComponent(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Top App Bar Wrapper",
                    subtitle = if (useExpressive) "Expressive subtitle variant enabled" else "",
                    canNavigateBack = true,
                    useExpressive = useExpressive,
                    onBackNavigate = {},
                )
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                TextComponent(
                    text = "Typography + Buttons",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                ButtonText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Navigate To Login",
                    onClick = onNavigateLogin,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ButtonIconAndText(
                        modifier = Modifier.weight(1f),
                        text = "Show Loader",
                        onClick = { showLoadingDialog = true },
                    )
                    ButtonText(
                        modifier = Modifier.weight(1f),
                        text = if (useExpressive) "Expressive: ON" else "Expressive: OFF",
                        isOutlined = true,
                        useExpressiveShapes = useExpressive,
                        onClick = { useExpressive = !useExpressive },
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ButtonIcon(
                        modifier = Modifier.weight(1f),
                        iconVector = Icons.Default.Favorite,
                        useExpressiveShapes = useExpressive,
                        onClick = {},
                    )
                    ButtonTextAndIcon(
                        modifier = Modifier.weight(2f),
                        text = "Text + Icon",
                        iconVector = Icons.Default.Settings,
                        useExpressiveShapes = useExpressive,
                        onClick = {},
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ButtonBack(useExpressiveShapes = useExpressive, onClick = {})
                    ButtonClose(useExpressiveShapes = useExpressive, onClick = {})
                    ButtonAction(
                        iconVector = Icons.Default.Person,
                        useExpressiveShapes = useExpressive,
                        onClick = {},
                    )
                }
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                TextComponent(
                    text = "Carousel Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                TextComponent(
                    text = "Expressive Text Carousel",
                    type = TextType.Title,
                    size = TextSize.Small,
                    fontWeight = FontWeight.Medium,
                )
                ExpressiveTextCarousel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.sdp),
                    items = carouselItems,
                    variant = if (useExpressive) {
                        ExpressiveCarouselVariant.MULTI_BROWSE
                    } else {
                        ExpressiveCarouselVariant.UNCONTAINED
                    },
                )
                TextComponent(
                    text = "Expressive Image Carousel",
                    type = TextType.Title,
                    size = TextSize.Small,
                    fontWeight = FontWeight.Medium,
                )
                ExpressiveImageCarousel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.sdp),
                    imageUrls = carouselImageUrls,
                    variant = if (useExpressive) {
                        ExpressiveCarouselVariant.CENTERED_HERO
                    } else {
                        ExpressiveCarouselVariant.UNCONTAINED
                    },
                )
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                TextComponent(
                    text = "Input Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Full Name",
                    leadingIcon = Icons.Default.Person,
                    trialingIcon = if (fullName.isEmpty()) null else Icons.Default.Close,
                    onClickTrailingIcon = { fullName = "" },
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    leadingIcon = Icons.Default.Email,
                    onClickTrailingIcon = {},
                )
                PasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    isError = password.isNotEmpty() && password.length < 6,
                    errorText = if (password.isNotEmpty() && password.length < 6) {
                        "Password must be at least 6 characters."
                    } else {
                        ""
                    },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    leadingIcon = Icons.Default.Lock,
                )
                DropdownField(
                    modifier = Modifier.fillMaxWidth(),
                    items = roleOptions,
                    itemContent = { it },
                    value = selectedRole.orEmpty(),
                    selectedItem = selectedRole,
                    onItemSelected = { selectedRole = it },
                    label = "Role",
                    leadingIcon = Icons.Default.Settings,
                    isEnabled = true,
                )
                CheckboxCustom(
                    isChecked = isPolicyAccepted,
                    onCheckedChange = { isPolicyAccepted = it },
                    useExpressiveStyle = useExpressive,
                    label = "I agree to continue with these reusable components.",
                )
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                TextComponent(
                    text = "Card Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                CardComponent(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.FILLED,
                    useExpressive = useExpressive,
                ) {
                    TextComponent(
                        modifier = Modifier.padding(12.sdp),
                        text = "Filled Card (Material 3 default variant).",
                        maxLines = 2,
                    )
                }
                CardComponent(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.ELEVATED,
                    useExpressive = useExpressive,
                ) {
                    TextComponent(
                        modifier = Modifier.padding(12.sdp),
                        text = "Elevated Card (default elevation and visible depth).",
                        maxLines = 2,
                    )
                }
                CardComponent(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.OUTLINED,
                    useExpressive = useExpressive,
                ) {
                    TextComponent(
                        modifier = Modifier.padding(12.sdp),
                        text = "Outlined Card (Material 3 default border style).",
                        maxLines = 2,
                    )
                }
                CardComponent(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.ELEVATED,
                    useExpressive = useExpressive,
                ) {
                    TextComponent(
                        modifier = Modifier.padding(12.sdp),
                        text = "Reusable Elevated Card for content sections.",
                        maxLines = 2,
                    )
                }
                CardComponent(
                    modifier = Modifier.fillMaxWidth(),
                    variant = CardVariant.FILLED,
                    useExpressive = useExpressive,
                ) {
                    TextComponent(
                        modifier = Modifier.padding(12.sdp),
                        text = "Reusable Filled Card for compact layouts.",
                        maxLines = 2,
                    )
                }
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                TextComponent(
                    text = "Progress Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                LoadingIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    isCircular = false,
                    progress = progress,
                    useExpressive = useExpressive,
                )
                LoadingIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    isCircular = false,
                    useExpressive = useExpressive,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    LoadingIndicator(
                        modifier = Modifier.size(56.sdp),
                        isCircular = true,
                        progress = progress,
                        useExpressive = useExpressive,
                    )
                    LoadingIndicator(
                        modifier = Modifier.size(56.sdp),
                        isCircular = true,
                        useExpressive = useExpressive,
                    )
                    TextComponent(
                        modifier = Modifier.weight(1f),
                        text = "Determinate + indeterminate demos",
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                    )
                }
            }
        }

        CardComponent(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp),
            ) {
                TextComponent(
                    text = "Lazy + Pager Components",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                HorizontalPagersWithTabs(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.sdp),
                    tabs = pagerTabs,
                    pageItems = pagerItems,
                    contentPadding = PaddingValues(horizontal = 20.sdp),
                    tabIndicator = { selectedIndex -> TabIndicator(selectedIndex) },
                    pageContent = { _, _, item ->
                        CardComponent(
                            modifier = Modifier.fillMaxWidth(),
                            variant = CardVariant.OUTLINED,
                            useExpressive = useExpressive,
                        ) {
                            TextComponent(
                                modifier = Modifier.padding(14.sdp),
                                text = item,
                                maxLines = 3,
                            )
                        }
                    }
                )
                BaseLazy(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.sdp),
                    items = lazyItems,
                    listType = LazyListType.LIST,
                    scrollDirection = LazyScrollDirection.VERTICAL,
                    userScrollEnabled = true,
                    isLoading = false,
                    useExpressiveLoadingIndicator = useExpressive,
                    onLoadMore = {},
                    onScrollStateChanged = { lazyHasScrolled = it },
                    itemContent = { index, item ->
                        CardComponent(
                            modifier = Modifier.fillMaxWidth(),
                            variant = CardVariant.ELEVATED,
                            useExpressive = useExpressive,
                        ) {
                            TextComponent(
                                modifier = Modifier.padding(12.sdp),
                                text = "${index + 1}. $item",
                                maxLines = 1,
                            )
                        }
                    }
                )
                TextComponent(
                    text = if (lazyHasScrolled) {
                        "Lazy list is scrolling."
                    } else {
                        "Lazy list is at start position."
                    },
                    size = TextSize.Small,
                )
            }
        }

        CardComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.sdp),
            variant = CardVariant.FILLED,
            useExpressive = useExpressive,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                TextComponent(
                    modifier = Modifier.padding(14.sdp),
                    text = "Expandable FAB Component Demo",
                    type = TextType.Title,
                    size = TextSize.Medium,
                    fontWeight = FontWeight.SemiBold,
                )
                ExpandableFabComponent(
                    modifier = Modifier.fillMaxSize(),
                    useExpressive = useExpressive,
                    items = fabActions,
                    mainFabIcon = Icons.Default.Add,
                    itemTitle = { it.title },
                    itemIcon = { it.icon },
                    onItemClick = { showBottomSheet = true }
                )
            }
        }

        ButtonText(
            modifier = Modifier.fillMaxWidth(),
            text = "Open Reusable Bottom Sheet",
            onClick = { showBottomSheet = true },
        )
    }
}