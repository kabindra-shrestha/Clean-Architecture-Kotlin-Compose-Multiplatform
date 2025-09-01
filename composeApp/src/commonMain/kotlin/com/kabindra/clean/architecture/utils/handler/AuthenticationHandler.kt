package com.kabindra.clean.architecture.utils.handler

import com.kabindra.clean.architecture.presentation.viewmodel.room.AuthenticationRoomViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthenticationHandler : KoinComponent {
    private val authenticationRoomViewModel: AuthenticationRoomViewModel by inject()

    fun logout(onNavigateLogin: () -> Unit) {
        authenticationRoomViewModel.logout()

        onNavigateLogin()
    }

}