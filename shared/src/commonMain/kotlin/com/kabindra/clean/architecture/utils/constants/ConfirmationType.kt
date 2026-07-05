package com.kabindra.clean.architecture.utils.constants

sealed class ConfirmationType {
    data object None : ConfirmationType()
    data object Logout : ConfirmationType()
}