package com.kabindra.architecture.presentation.ui.screen

import Route
import Screens
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kabindra.architecture.domain.entity.Article
import com.kabindra.architecture.presentation.ui.component.TopAppBarComponent
import org.koin.compose.koinInject
import kotlin.reflect.typeOf

@Composable
fun NewsScreen(
    navController: NavHostController = rememberNavController()
) {
    val snackBarHostState: SnackbarHostState = koinInject()

// Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
// Get the name of the current screen
    val currentRoute = backStackEntry?.destination?.route ?: ""
    val currentScreen =
        if (currentRoute.contains("Route.NewsListRoute")) {
            Screens.News.title
        } else if (currentRoute.contains("Route.NewsDetailRoute")) {
            Screens.NewsDetails.title
        } else {
            ""
        }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = currentScreen,
                iconButton = Icons.Filled.ArrowBack,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.NewsListRoute,
            modifier = Modifier
                .fillMaxSize()
            // .verticalScroll(rememberScrollState())
            // .padding(innerPadding)
        ) {
            composable<Route.NewsListRoute> {
                NewsListScreen(innerPadding = innerPadding)
                {
                    navController.navigate(Route.NewsDetailRoute(article = it))
                }
            }
            composable<Route.NewsDetailRoute>(
                typeMap = mapOf(
                    typeOf<Article>() to NewsNavType.ArticleType
                )
            ) { entry ->
                val arguments = entry.toRoute<Route.NewsDetailRoute>()
                NewsDetailScreen(innerPadding = innerPadding, article = arguments.article)
            }
        }
    }
}