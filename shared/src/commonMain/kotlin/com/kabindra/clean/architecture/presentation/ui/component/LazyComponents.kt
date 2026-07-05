package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import network.chaintech.sdpcomposemultiplatform.sdp

// Enums for List and Grid Types
enum class LazyListType { LIST, GRID }
enum class LazyScrollDirection { VERTICAL, HORIZONTAL }

// Sealed class for different types of list items
sealed class ListItemContent {
    data class TextItem(val text: String) : ListItemContent()
    data class ImageItem(val imageUrl: String) : ListItemContent()
    // Add other item types as needed
}

// Base Lazy Component
@Composable
fun <T> BaseLazy(
    modifier: Modifier = Modifier.fillMaxSize(),
    contentPadding: PaddingValues = PaddingValues(5.sdp),
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(7.sdp),
    items: List<T>,
    listType: LazyListType = LazyListType.LIST,
    scrollDirection: LazyScrollDirection = LazyScrollDirection.VERTICAL,
    userScrollEnabled: Boolean = true,
    spanCount: Int = 2,
    isLoading: Boolean = false,
    useExpressiveLoadingIndicator: Boolean = true,
    loadMoreThreshold: Int = 5,
    currentPage: Int = 1,
    totalPages: Int = 1,
    onLoadMore: (Int) -> Unit,
    itemContent: @Composable (Int, T) -> Unit,
    loadingContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.sdp),
            contentAlignment = Alignment.Center
        ) {
            LoadingIndicator(
                modifier = Modifier.align(Alignment.Center),
                isCircular = true,
                useExpressive = useExpressiveLoadingIndicator
            )
        }
    },
    emptyContent: @Composable () -> Unit = {},
    errorContent: @Composable (String) -> Unit = {},
    onScrollStateChanged: (Boolean) -> Unit
) {
    if (items.isEmpty() && !isLoading) {
        emptyContent()
        return
    }

    when (listType) {
        LazyListType.LIST -> LazyList(
            modifier, contentPadding, arrangement, items, scrollDirection,
            userScrollEnabled, isLoading, loadMoreThreshold, currentPage, totalPages,
            onLoadMore, itemContent, loadingContent, onScrollStateChanged
        )

        LazyListType.GRID -> LazyGrid(
            modifier, contentPadding, arrangement, items, scrollDirection,
            userScrollEnabled, spanCount, isLoading, loadMoreThreshold, currentPage, totalPages,
            onLoadMore, itemContent, loadingContent, onScrollStateChanged
        )
    }
}

// Lazy List Implementation
@Composable
fun <T> LazyList(
    modifier: Modifier,
    contentPadding: PaddingValues,
    arrangement: Arrangement.HorizontalOrVertical,
    items: List<T>,
    scrollDirection: LazyScrollDirection,
    userScrollEnabled: Boolean,
    isLoading: Boolean,
    loadMoreThreshold: Int,
    currentPageNew: Int,
    totalPagesNew: Int,
    onLoadMore: (Int) -> Unit,
    itemContent: @Composable (Int, T) -> Unit,
    loadingContent: @Composable () -> Unit,
    onScrollStateChanged: (Boolean) -> Unit
) {
    val currentPage = remember(currentPageNew) { mutableIntStateOf(currentPageNew) }
    val totalPages = totalPagesNew

    val listState = rememberLazyListState()

    val isCollapsed = remember { derivedStateOf { listState.firstVisibleItemScrollOffset > 0 } }

    LaunchedEffect(isCollapsed.value) { onScrollStateChanged(isCollapsed.value) }

    // Derived state to determine when to load more items
    val shouldLoadMore = remember {
        derivedStateOf {
            // Get the total number of items in the list
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            // Get the index of the last visible item
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            // Check if we have scrolled near the end of the list and more items should be loaded
            totalItemsCount > 0 &&
                    lastVisibleItemIndex >= (totalItemsCount - loadMoreThreshold) &&
                    !isLoading
        }
    }

    // Launch a coroutine to load more items when shouldLoadMore becomes true
    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                if (!isLoading && currentPage.intValue < totalPages) {
                    currentPage.intValue += 1
                    onLoadMore(currentPage.intValue)
                }
            }
    }

    when (scrollDirection) {
        LazyScrollDirection.VERTICAL -> LazyColumn(
            state = listState, modifier = modifier, contentPadding = contentPadding,
            verticalArrangement = arrangement, userScrollEnabled = userScrollEnabled
        ) {
            itemsIndexed(items) { index, item -> itemContent(index, item) }
            if (isLoading) item { loadingContent() }
        }

        LazyScrollDirection.HORIZONTAL -> LazyRow(
            state = listState, modifier = modifier, contentPadding = contentPadding,
            horizontalArrangement = arrangement, userScrollEnabled = userScrollEnabled
        ) {
            itemsIndexed(items) { index, item -> itemContent(index, item) }
            if (isLoading) item { loadingContent() }
        }
    }
}

// Lazy Grid Implementation
@Composable
fun <T> LazyGrid(
    modifier: Modifier,
    contentPadding: PaddingValues,
    arrangement: Arrangement.HorizontalOrVertical,
    items: List<T>,
    scrollDirection: LazyScrollDirection,
    userScrollEnabled: Boolean,
    spanCount: Int,
    isLoading: Boolean,
    loadMoreThreshold: Int,
    currentPageNew: Int,
    totalPagesNew: Int,
    onLoadMore: (Int) -> Unit,
    itemContent: @Composable (Int, T) -> Unit,
    loadingContent: @Composable () -> Unit,
    onScrollStateChanged: (Boolean) -> Unit
) {
    val currentPage = remember(currentPageNew) { mutableIntStateOf(currentPageNew) }
    val totalPages = totalPagesNew

    val gridState = rememberLazyGridState()

    val isCollapsed = remember { derivedStateOf { gridState.firstVisibleItemScrollOffset > 0 } }

    LaunchedEffect(isCollapsed.value) { onScrollStateChanged(isCollapsed.value) }

    // Derived state to determine when to load more items
    val shouldLoadMore = remember {
        derivedStateOf {
            // Get the total number of items in the list
            val totalItemsCount = gridState.layoutInfo.totalItemsCount
            // Get the index of the last visible item
            val lastVisibleItemIndex =
                gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            // Check if we have scrolled near the end of the list and more items should be loaded
            totalItemsCount > 0 &&
                    lastVisibleItemIndex >= (totalItemsCount - loadMoreThreshold) &&
                    !isLoading
        }
    }

    // Launch a coroutine to load more items when shouldLoadMore becomes true
    LaunchedEffect(gridState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                if (!isLoading && currentPage.intValue < totalPages) {
                    currentPage.intValue += 1
                    onLoadMore(currentPage.intValue)
                }
            }
    }

    when (scrollDirection) {
        LazyScrollDirection.VERTICAL -> LazyVerticalGrid(
            columns = GridCells.Fixed(spanCount), state = gridState, modifier = modifier,
            contentPadding = contentPadding, verticalArrangement = arrangement,
            horizontalArrangement = arrangement, userScrollEnabled = userScrollEnabled
        ) {
            itemsIndexed(items) { index, item -> itemContent(index, item) }
            if (isLoading) item(span = { GridItemSpan(maxLineSpan) }) { // Span all columns
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    loadingContent()
                }
            }
        }

        LazyScrollDirection.HORIZONTAL -> LazyHorizontalGrid(
            rows = GridCells.Fixed(spanCount), state = gridState, modifier = modifier,
            contentPadding = contentPadding, verticalArrangement = arrangement,
            horizontalArrangement = arrangement, userScrollEnabled = userScrollEnabled
        ) {
            itemsIndexed(items) { index, item -> itemContent(index, item) }
            if (isLoading) item(span = { GridItemSpan(maxLineSpan) }) { // Span all columns
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    loadingContent()
                }
            }
        }
    }
}
