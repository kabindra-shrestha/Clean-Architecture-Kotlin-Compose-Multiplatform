package com.kabindra.clean.architecture.utils.constants

sealed class MessageType {
    data object Success : MessageType()
    data object Error : MessageType()
    data object Confirmation : MessageType()
}