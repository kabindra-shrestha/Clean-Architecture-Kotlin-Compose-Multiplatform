package com.kabindra.clean.architecture.presentation.ui.screen.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kabindra.clean.architecture.presentation.ui.screen.splash.DashboardScreen
import com.kabindra.clean.architecture.presentation.ui.screen.splash.LoginScreen
import com.kabindra.clean.architecture.presentation.ui.screen.splash.LoginVerifyOTPScreen
import com.kabindra.clean.architecture.presentation.ui.screen.splash.RegisterScreen
import com.kabindra.clean.architecture.presentation.ui.screen.splash.SplashScreen
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val snackBarHostState: SnackbarHostState = koinInject()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.SplashRoute,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable<Route.SplashRoute> {
                SplashScreen(
                    innerPadding = innerPadding,
                    onNavigateLogin = {
                        navController.navigate(Route.LoginMainRoute) {
                            popUpTo(Route.SplashRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateDashboard = {
                        navController.navigate(Route.DashboardRoute) {
                            popUpTo(Route.SplashRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            navigation<Route.LoginMainRoute>(startDestination = Route.LoginRoute) {
                composable<Route.RegisterRoute> {
                    /*RegisterScreen(
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBackNavigate = { AppBackHandler(navController) }
                    )*/
                    RegisterScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable<Route.LoginRoute> {
                    /*LoginScreen(
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateLoginVerifyOTP = { username,
                                                     appLoginCode ->
                            navController.navigate(
                                Route.LoginVerifyOTPRoute(
                                    username = username,
                                    appLoginCode = appLoginCode
                                )
                            )
                        },
                        onNavigateDashboard = {
                            // Navigate To Dashboard
                        }
                    )*/
                    LoginScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable<Route.LoginVerifyOTPRoute> { entry ->
                    val arguments = entry.toRoute<Route.LoginVerifyOTPRoute>()
                    /*LoginVerifyOTPScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateDashboard = {
                            // Navigate To Dashboard
                        },
                        onBackNavigate = {
                            AppBackHandler(navController)
                        },
                        usernameArgument = arguments.username,
                        appLoginCodeArgument = arguments.appLoginCode
                    )*/
                    LoginVerifyOTPScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginVerifyOTPRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }

            composable<Route.DashboardRoute> {
                DashboardScreen(
                    innerPadding = innerPadding,
                    onNavigateLogin = {
                        navController.navigate(Route.LoginMainRoute) {
                            popUpTo(Route.DashboardRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

fun AppBackHandler(navController: NavHostController) {
    if (navController.previousBackStackEntry != null) {
        navController.popBackStack()
    } else {
        navController.popBackStack()
    }
}