package com.kabindra.clean.architecture.presentation.ui.screen.navigation

/*
fun NavGraphBuilder.navigationLogin(
    navController: NavHostController,
) {

    navigation<Route.LoginRoute>(startDestination = Route.LoginMainRoute) {
        composable<Route.LoginMainRoute> {
            LoginScreen(onNavigateLogin = { navController.navigate(Route.DashboardRoute) },
                onNavigateForgetPassword = { navController.navigate(Route.ForgetPasswordRoute) },
                onNavigateLoginQrScanner = { navController.navigate(Route.LoginQrScannerRoute) })
        }

        composable<Route.LoginQrScannerRoute> {
            QrScannerLoginScreen(
                onNavigateLogin = { username,
                                    userCode ->
                    navController.navigate(
                        Route.LoginMainRoute(
                            username = username,
                            userCode = userCode
                        )
                    )
                })
        }
    }


}
*/
