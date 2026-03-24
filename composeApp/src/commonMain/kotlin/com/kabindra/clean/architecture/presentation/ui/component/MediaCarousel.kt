package com.kabindra.clean.architecture.presentation.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.kabindra.clean.architecture.presentation.ui.theme.carouselSelected
import com.kabindra.clean.architecture.presentation.ui.theme.carouselUnselected
import com.kabindra.clean.architecture.presentation.ui.theme.tabSelected
import com.kabindra.clean.architecture.presentation.ui.theme.tabUnselected
import com.kabindra.clean.architecture.presentation.ui.theme.transparent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import kotlin.math.absoluteValue

@Composable
fun PagerIndicator(modifier: Modifier = Modifier, pageCount: Int, currentPageIndex: Int) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 16.sdp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPageIndex == iteration) carouselSelected else carouselUnselected
            Box(
                modifier = Modifier
                    .width(16.sdp)
                    .height(4.sdp)
                    .padding(
                        start = 2.sdp,
                        end = 2.sdp
                    )
                    .clip(RoundedCornerShape(8.sdp))
                    .background(color)
            )
        }
    }
}

@Composable
fun TabIndicatorScope.TabIndicator(
    selectedIndex: Int
) {
    Box(
        modifier = Modifier
            .tabIndicatorOffset(selectedIndex)
            .height(4.sdp)
            .padding(horizontal = 28.sdp)
            .clip(RoundedCornerShape(8.sdp))
            .background(tabSelected)
    )
}

@Composable
fun <T> HorizontalPagers(
    modifier: Modifier = Modifier.fillMaxWidth(),
    aspectRatio: Float = 16f / 9f,
    padding: Dp = 0.dp,
    contentPadding: PaddingValues = PaddingValues(20.sdp),
    pageSpacing: Dp = 0.dp,
    useGraphicsLayer: Boolean = false,
    isAutoScroll: Boolean = false,
    showIndicator: Boolean = true,
    pageItems: List<T>,
    pageContent: @Composable (T) -> Unit, // Generic content renderer
) {
    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .padding(top = padding)
    ) {
        val pagerState = rememberPagerState(pageCount = { pageItems.size })
        val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

        val pageInteractionSource = remember { MutableInteractionSource() }
        val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

        // Stop auto-advancing when pager is dragged or one of the pages is pressed
        val autoScroll = !pagerIsDragged && !pageIsPressed

        if (isAutoScroll && autoScroll) {
            LaunchedEffect(pagerState, pageInteractionSource) {
                while (true) {
                    delay(2000)
                    val nextPage = (pagerState.currentPage + 1) % pageItems.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            contentPadding = contentPadding,
            pageSpacing = pageSpacing
        ) { page ->
            Card(
                modifier = modifier
                    .aspectRatio(aspectRatio)
                    .clip(RoundedCornerShape(16.sdp))
                    .clickable(
                        interactionSource = pageInteractionSource,
                        indication = ripple(bounded = true),
                    ) {
                        // Handle page click
                    }
                    .wrapContentSize(align = Alignment.Center)
                    .then(
                        if (useGraphicsLayer) {
                            Modifier.graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset =
                                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                                scaleX = lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                scaleY = lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                // We animate the alpha, between 25% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                        } else {
                            Modifier
                        })

            ) {
                // Render custom page content
                pageContent(pageItems[page])
            }
        }

        if (showIndicator) {
            PagerIndicator(
                modifier = modifier
                    .align(Alignment.BottomCenter),
                pageCount = pageItems.size,
                currentPageIndex = pagerState.currentPage
            )
        }
    }
}

@Composable
fun <T> HorizontalPagersWithTabs(
    modifier: Modifier = Modifier.fillMaxWidth(),
    padding: Dp = 0.dp,
    contentPadding: PaddingValues = PaddingValues(20.sdp),
    pageSpacing: Dp = 0.dp,
    useGraphicsLayer: Boolean = false,
    tabs: List<String>,
    tabColor: Color = transparent,
    tabIndicator: @Composable TabIndicatorScope.(selectedIndex: Int) -> Unit = { _ ->
        // TabIndicator(selectedIndex)
    },
    tabsIcon: Boolean = false,
    pageItems: List<T>,
    initialPage: Int? = 0,
    pageContent: @Composable (currentPageIndex: Int, pageIndex: Int, T) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initialPage ?: 0, pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(top = padding)) {
        PrimaryScrollableTabRow(
            modifier = Modifier.padding(bottom = 10.sdp),
            containerColor = tabColor,
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            indicator = {
                tabIndicator(pagerState.currentPage)
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index

                Tab(
                    selected = isSelected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .height(36.sdp)
                                .clip(RoundedCornerShape(12.sdp))
                                .background(
                                    if (isSelected)
                                        tabSelected.copy(alpha = 0.2f)
                                    else
                                        tabUnselected
                                )
                                .border(
                                    width = 1.sdp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(12.sdp)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val tabTitle = @Composable {
                                TextComponent(text = title)
                            }

                            if (tabsIcon) {
                                Spacer(modifier = Modifier.width(8.sdp))
                                ImageHandlerVector(
                                    modifier = Modifier.size(24.sdp),
                                    image = Icons.Default.Person
                                )
                                Spacer(modifier = Modifier.width(8.sdp))
                                if (isSelected) {
                                    tabTitle()
                                    Spacer(modifier = Modifier.width(12.sdp))
                                }
                            } else {
                                Spacer(modifier = Modifier.width(12.sdp))
                                tabTitle()
                                Spacer(modifier = Modifier.width(12.sdp))
                            }
                        }
                    },
                )
            }
        }

        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            contentPadding = contentPadding,
            pageSpacing = pageSpacing,
            beyondViewportPageCount = 0
        ) { page ->
            Box(
                modifier = modifier
                    .wrapContentSize(align = Alignment.Center)
                    .then(
                        if (useGraphicsLayer) {
                            Modifier.graphicsLayer {
                                val pageOffset =
                                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                                scaleX = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                                scaleY = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                                alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                            }
                        } else {
                            Modifier
                        }
                    )
            ) {
                pageContent(pagerState.currentPage, page, pageItems[page])
            }
        }
    }
}

