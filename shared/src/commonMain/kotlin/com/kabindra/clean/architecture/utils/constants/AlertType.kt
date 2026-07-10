package com.kabindra.clean.architecture.utils.constants

sealed class AlertType {
    data object None : AlertType()
    data object Snackbar : AlertType()
    data object Toast : AlertType()
    data object Dialog : AlertType()
}