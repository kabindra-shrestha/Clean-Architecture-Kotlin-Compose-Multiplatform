package com.kabindra.clean.architecture.presentation.ui.screen.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.kabindra.clean.architecture.domain.entity.Owner
import com.kabindra.clean.architecture.presentation.ui.component.ButtonText
import com.kabindra.clean.architecture.presentation.ui.component.DropdownField
import com.kabindra.clean.architecture.presentation.ui.component.InputField
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.ui.theme.approve
import com.kabindra.clean.architecture.utils.enums.TicketRemarkType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketCommentsBottomSheet(
    owners: List<Owner>,
    remarks: String,
    onSend: (ownersField: Owner?, commentsField: String?) -> Unit,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var ownersField by remember { mutableStateOf<Owner?>(null) }
    var isOwnersFieldValid by remember { mutableStateOf(false) }
    var ownersFieldError by remember { mutableStateOf("") }
    var commentsField by remember { mutableStateOf("") }
    var isCommentsFieldValid by remember { mutableStateOf(false) }
    var commentsFieldError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = AppTheme.dimens.paddingNormal)
            .clickable(
                interactionSource = MutableInteractionSource(), indication = null,
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
    ) {
        if (owners.isNotEmpty()) {
            DropdownField(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.paddingSmall),
                items = owners,
                itemContent = { owners -> owners.name ?: "" },
                value = if (ownersField != null) {
                    ownersField?.name!!
                } else {
                    ""
                },
                selectedItem = ownersField,
                onItemSelected = {
                    ownersField = it

                    if (ownersField == null) {
                        isOwnersFieldValid = false
                        ownersFieldError = "Please select a owner"
                    } else {
                        isOwnersFieldValid = true
                        ownersFieldError = ""
                    }
                },
                label = "Select a Owner",
                leadingIcon = null,
                isError = isOwnersFieldValid,
                errorText = ownersFieldError
            )
        } else {
            isOwnersFieldValid = true
            ownersFieldError = ""
        }

        if (remarks == TicketRemarkType.Required.type || remarks == TicketRemarkType.Optional.type) {
            InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.paddingSmall),
                value = commentsField,
                onValueChange = {
                    commentsField = it

                    if (remarks == TicketRemarkType.Required.type && commentsField.isBlank()) {
                        isCommentsFieldValid = false
                        commentsFieldError = "Comments must not be empty"
                    } else {
                        isCommentsFieldValid = true
                        commentsFieldError = ""
                    }
                },
                label = "Comments",
                isError = isCommentsFieldValid,
                errorText = commentsFieldError,
                imeAction = ImeAction.Done,
                onClickTrailingIcon = {}
            )
        } else {
            isCommentsFieldValid = true
            commentsFieldError = ""
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonText(
                modifier = Modifier.wrapContentWidth(),
                text = "Send",
                buttonColors = ButtonDefaults.buttonColors(containerColor = approve)
            ) {
                keyboardController?.hide()
                focusManager.clearFocus()

                if (owners.isNotEmpty()) {
                    if (ownersField == null) {
                        isOwnersFieldValid = false
                        ownersFieldError = "Please select a owner"
                    } else {
                        isOwnersFieldValid = true
                        ownersFieldError = ""
                    }
                } else {
                    isOwnersFieldValid = true
                    ownersFieldError = ""
                }

                if (remarks == TicketRemarkType.Required.type || remarks == TicketRemarkType.Optional.type) {
                    if (remarks == TicketRemarkType.Required.type && commentsField.isBlank()) {
                        isCommentsFieldValid = false
                        commentsFieldError = "Comments must not be empty"
                    } else {
                        isCommentsFieldValid = true
                        commentsFieldError = ""
                    }
                } else {
                    isCommentsFieldValid = true
                    commentsFieldError = ""
                }

                if (isOwnersFieldValid && isCommentsFieldValid) {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    onSend(ownersField, commentsField)
                }
            }

            ButtonText(
                modifier = Modifier.wrapContentWidth(),
                text = "Close"
            ) {
                keyboardController?.hide()
                focusManager.clearFocus()

                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onClose()
                    }
                }
            }
        }
    }
}