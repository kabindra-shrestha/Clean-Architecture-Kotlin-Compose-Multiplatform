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
import com.kabindra.clean.architecture.presentation.ui.screen.drawer.DashboardScreen
import com.kabindra.clean.architecture.presentation.ui.screen.login.LoginQRScannerScreen
import com.kabindra.clean.architecture.presentation.ui.screen.login.LoginQRScreen
import com.kabindra.clean.architecture.presentation.ui.screen.login.LoginVerifyOTPScreen
import com.kabindra.clean.architecture.presentation.ui.screen.login.MPINSetScreen
import com.kabindra.clean.architecture.presentation.ui.screen.login.MPINVerifyScreen
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
                    onNavigateMPINSet = {
                        navController.navigate(Route.MPINSetRoute) {
                            popUpTo(Route.SplashRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateMPINVerify = {
                        navController.navigate(Route.MPINVerifyRoute) {
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
                composable<Route.LoginRoute> {
                    LoginQRScreen(
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateLoginQrScanner = {
                            navController.navigate(Route.LoginQrScannerRoute)
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
                        onNavigateMPINSet = {
                            navController.navigate(Route.MPINSetRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateMPINVerify = {
                            navController.navigate(Route.MPINVerifyRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateDashboard = {
                            navController.navigate(Route.DashboardRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable<Route.LoginQrScannerRoute> {
                    LoginQRScannerScreen(
                        onBackNavigate = { AppBackHandler(navController) }
                    )
                }

                composable<Route.LoginVerifyOTPRoute> { entry ->
                    val arguments = entry.toRoute<Route.LoginVerifyOTPRoute>()
                    LoginVerifyOTPScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateMPINSet = {
                            navController.navigate(Route.MPINSetRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateMPINVerify = {
                            navController.navigate(Route.MPINVerifyRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateDashboard = {
                            navController.navigate(Route.DashboardRoute) {
                                popUpTo(Route.LoginMainRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBackNavigate = {
                            AppBackHandler(navController)
                        },
                        usernameArgument = arguments.username,
                        appLoginCodeArgument = arguments.appLoginCode
                    )
                }

                composable<Route.MPINSetRoute> {
                    MPINSetScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.MPINSetRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateDashboard = {
                            navController.navigate(Route.DashboardRoute) {
                                popUpTo(Route.MPINSetRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBackNavigate = { AppBackHandler(navController) },
                    )
                }

                composable<Route.MPINVerifyRoute> {
                    MPINVerifyScreen(
                        innerPadding = innerPadding,
                        onNavigateLogin = {
                            navController.navigate(Route.LoginMainRoute) {
                                popUpTo(Route.MPINVerifyRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateDashboard = {
                            navController.navigate(Route.DashboardRoute) {
                                popUpTo(Route.MPINVerifyRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onBackNavigate = { AppBackHandler(navController) },
                    )
                }
            }

            composable<Route.DashboardRoute> {
                DashboardScreen(
                    onNavigateLogin = {
                        navController.navigate(Route.LoginMainRoute) {
                            popUpTo(Route.DashboardRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    })
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