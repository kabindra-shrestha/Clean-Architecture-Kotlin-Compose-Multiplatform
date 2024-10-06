@file:OptIn(KoinExperimentalAPI::class)

package com.kabindra.architecture.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabindra.architecture.domain.entity.Article
import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.entity.Source
import com.kabindra.architecture.presentation.ui.component.AlertDialog
import com.kabindra.architecture.presentation.ui.component.CustomProgressDialog
import com.kabindra.architecture.presentation.viewmodel.NewsViewModel
import com.kabindra.architecture.utils.Connectivity
import com.kabindra.architecture.utils.Result
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Main composable function for displaying the news screen.
 *
 * This function collects the state of news articles from the ViewModel
 * and fetches the news when it is first launched. It displays a list
 * of news articles using a LazyColumn for efficient scrolling.
 */
@Composable
fun NewsListScreen(
    viewModel: NewsViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onNewsClicked: (Article) -> Unit
) {
    val connectivity = remember { Connectivity() }

    val isConnected by connectivity.isConnectedState.collectAsState()
    val connectionStatus by connectivity.currentNetworkConnectionState.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    // Collecting the state of news articles from the ViewModel
    val newsState by viewModel.newsState.collectAsState()

    if (isConnected) {
        // Fetching news when the composable is launched
        LaunchedEffect(Unit) {
            viewModel.loadNews()
        }

        showErrorDialog = false
    } else {
        showErrorDialog = true
    }

    if (showErrorDialog) {
        AlertDialog(
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            onConfirm = {
                showErrorDialog = false
            },
            onDismiss = {
                showErrorDialog = false
            }
        )
    }

    val news: MutableList<Article>

    when (newsState) {
        is Result.Initial -> {}
        is Result.Loading -> {
            CustomProgressDialog(isVisible = true)
        }

        is Result.Success -> {
            news = (newsState as Result.Success<News>).data.articles as MutableList<Article>

            // Text(DeviceDetails().deviceDetails())

            // Displaying the list of news articles
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(innerPadding)
                    .padding(innerPadding),
                contentPadding = PaddingValues(12.dp), // Adds padding around LazyColumn (top, bottom, start, end)
                verticalArrangement = Arrangement.spacedBy(12.dp), // Adds space between items vertically
            ) {
                items(news.size) { item ->
                    NewsItem(news[item]) { item ->
                        onNewsClicked(item)
                    }
                }
            }
        }

        is Result.Error -> Text("Error: ${(newsState as Result.Error).exception?.message}")
    }
}

/**
 * Composable function to display a single news item.
 *
 * This function creates a vertical layout for the news article,
 * displaying the title, author, publication date, and description.
 * An optional image can be loaded if a URL is provided.
 */
@Composable
fun NewsItem(
    article: Article,
    onNewsClicked: (Article) -> Unit
) {
    val snackBarHostState: SnackbarHostState = koinInject()
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                scope.launch {
                    /*snackBarHostState.showSnackbar(
                        message = "You clicked ${article.title}",
                        actionLabel = "Dismiss"
                    )*/
                    onNewsClicked(article)
                }
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Display the title of the article with bold text and larger font size
            Text(
                text = article.title!!,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            )

            // Display the author if available with normal weight
            article.author?.let {
                Text(
                    text = "by $it",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            // Display the publication date with smaller font size
            Text(
                text = article.publishedAt!!,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            )

            // Display the description if available with normal weight
            article.description?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

// Preview function to display a sample news item in the Compose preview
@Preview
@Composable
fun NewsItemPreview() {
    NewsItem(
        Article(
            source = Source(id = "1", name = "Example Source"),
            author = "Author Name",
            title = "Sample Title",
            description = "Sample Description",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = "2024-01-01T00:00:00Z",
            content = "Sample Content"
        )
    ) {}
}