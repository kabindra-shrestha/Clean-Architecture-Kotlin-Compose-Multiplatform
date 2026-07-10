package com.kabindra.clean.architecture.presentation.ui.screen.splash

import com.kabindra.clean.architecture.domain.entity.LoginRefreshUserDetails
import com.kabindra.clean.architecture.domain.entity.User

data class SplashState(
    val isLoading: Boolean = false,
    val loginRefreshUserDetails: LoginRefreshUserDetails? = null,
    val isLogged: Boolean? = null,
    val user: User? = null
)
