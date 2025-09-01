package com.kabindra.clean.architecture.presentation.ui.screen.ticket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.HorizontalPagersWithTabs
import com.kabindra.clean.architecture.presentation.ui.component.TopAppBarWithBackComponent
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.enums.TicketContentType
import com.kabindra.clean.architecture.utils.enums.getTicketContentTypeIndex
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog

private lateinit var content: String
private lateinit var workflow: String

@Composable
fun TicketScreen(
    onNavigateLogin: () -> Unit,
    onBackNavigate: () -> Unit,
    contentArgument: String,
    workflowArgument: String,
) {
    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()
    var errorStatusCode by remember { mutableStateOf(-1) }

    content = contentArgument
    workflow = workflowArgument

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

    Column(
        modifier = Modifier.fillMaxSize().padding(
            top = 12.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = AppTheme.dimens.bottomNavigationPadding
        ),
    ) {
        TopAppBarWithBackComponent(
            title = "My Tickets",
            onBackNavigate = { onBackNavigate() })

        val tabs = TicketContentType.entries.map { it.title }
        val pageItems = TicketContentType.entries.map { it.slug }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var initialPage: Int? = 0

            if (content.isNotEmpty()) {
                initialPage = getTicketContentTypeIndex<TicketContentType>(
                    slug = content
                )
            }

            HorizontalPagersWithTabs(
                modifier = Modifier.fillMaxSize(),
                padding = 0.dp,
                contentPadding = PaddingValues(0.dp),
                pageSpacing = 0.dp,
                useGraphicsLayer = true,
                tabs = tabs,
                pageItems = pageItems,
                initialPage = initialPage
            ) { currentPageIndex, pageIndex, pageContent ->
                TicketViewScreen(
                    currentPageIndex = currentPageIndex,
                    pageIndex = pageIndex,
                    content = pageContent,
                    workflow = workflow,
                    onNavigateLogin = { onNavigateLogin() })
            }
        }
    }
}