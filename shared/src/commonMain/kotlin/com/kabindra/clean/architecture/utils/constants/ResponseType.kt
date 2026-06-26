package com.kabindra.clean.architecture.utils.constants

sealed class ResponseType {
    data object None : ResponseType()
    data object VersionCheckUpdate : ResponseType()
    data object LoginCheckUser : ResponseType()
    data object LoginVerifyOTP : ResponseType()
    data object Logout : ResponseType()
    data object Refresh : ResponseType()
}