package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldDefault
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldError
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldLabelDefault
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldLabelError
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldTextDefault
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldTextError
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    leadingIcon: ImageVector? = null,
    trialingIcon: ImageVector? = null,
    isEnabled: Boolean = true,
    onClickTrailingIcon: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val leadingIcons: (@Composable () -> Unit)? = leadingIcon?.let {
        {
            ImageHandlerVector(
                modifier = Modifier
                    .size(20.sdp)
                    .aspectRatio(1f / 1f),
                image = it,
                contentDescription = ""
            )
        }
    }

    val trailingIcons: (@Composable () -> Unit)? = trialingIcon?.let {
        {
            ImageHandlerVector(
                modifier = Modifier
                    .size(20.sdp)
                    .aspectRatio(1f / 1f),
                image = it,
                contentDescription = "",
                isClickable = true,
                onClick = { onClickTrailingIcon() }
            )
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { TextComponent(text = label) },
        isError = isError,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = inputFieldDefault,
            focusedTextColor = inputFieldTextDefault,
            focusedLabelColor = inputFieldLabelDefault,
            errorBorderColor = inputFieldError,
            errorTextColor = inputFieldTextError,
            errorLabelColor = inputFieldLabelError,
            disabledBorderColor = inputFieldDefault,
            disabledTextColor = inputFieldTextDefault,
            disabledLabelColor = inputFieldLabelDefault
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions =
            if (imeAction == ImeAction.Done) {
                KeyboardActions(onDone = { focusManager.clearFocus() })
            } else {
                KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            },
        leadingIcon = leadingIcons,
        trailingIcon = trailingIcons,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        enabled = isEnabled,
        supportingText = {
            if (isError && errorText.isNotEmpty()) {
                TextComponent(text = errorText, maxLines = 2)
            }
        }
    )
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorText: String = "",
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Next,
    leadingIcon: ImageVector? = null
) {
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcons: (@Composable () -> Unit)? = leadingIcon?.let {
        {
            ImageHandlerVector(
                modifier = Modifier
                    .size(20.sdp)
                    .aspectRatio(1f / 1f),
                image = it,
                contentDescription = ""
            )
        }
    }

    val trailingIcons = @Composable {
        ImageHandlerVector(
            modifier = Modifier
                .size(20.sdp)
                .aspectRatio(1f / 1f),
            image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = "",
            onClick = { isPasswordVisible = !isPasswordVisible }
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { TextComponent(text = label) },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = inputFieldDefault,
            focusedTextColor = inputFieldTextDefault,
            focusedLabelColor = inputFieldLabelDefault,
            errorBorderColor = inputFieldError,
            errorTextColor = inputFieldTextError,
            errorLabelColor = inputFieldLabelError,
            disabledBorderColor = inputFieldDefault,
            disabledTextColor = inputFieldTextDefault,
            disabledLabelColor = inputFieldLabelDefault
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions =
            if (imeAction == ImeAction.Done) {
                KeyboardActions(onDone = { focusManager.clearFocus() })
            } else {
                KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            },
        leadingIcon = leadingIcons,
        trailingIcon = trailingIcons,
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = {
            if (isError && errorText.isNotEmpty()) {
                TextComponent(text = errorText, maxLines = 2)
            }
        }
    )
}

@Composable
fun <T> DropdownField(
    modifier: Modifier = Modifier,      // Modifier for customization
    items: List<T>,                     // List of items to display
    itemContent: (T) -> String,         // Content for each item
    value: String,                      // Currently selected value
    selectedItem: T?,                   // Currently selected item
    onItemSelected: (T) -> Unit,        // Callback for item selection
    label: String,                      // Optional label
    isError: Boolean = false,           // Whether to show an error state
    errorText: String = "",             // Validation message
    leadingIcon: ImageVector? = null,
    isEnabled: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldWidth by remember { mutableIntStateOf(0) }

    val leadingIcons: (@Composable () -> Unit)? = leadingIcon?.let {
        {
            ImageHandlerVector(
                modifier = Modifier
                    .size(20.sdp)
                    .aspectRatio(1f / 1f),
                image = it,
                contentDescription = ""
            )
        }
    }

    val trailingIcons: (@Composable () -> Unit) = {
        ImageHandlerVector(
            modifier = Modifier
                .size(20.sdp)
                .aspectRatio(1f / 1f),
            image = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
            contentDescription = "",
            isClickable = false,
        )
    }
    // Dropdown Field with DropdownMenu positioned correctly
    Box(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {}, // Disable user input as it's a dropdown
            readOnly = true, // Prevent manual text entry
            label = { TextComponent(text = label) },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { textFieldWidth = it.width }
                .clickable { expanded = !expanded }, // Make the entire field clickable
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = inputFieldDefault,
                focusedTextColor = inputFieldTextDefault,
                focusedLabelColor = inputFieldLabelDefault,
                errorBorderColor = inputFieldError,
                errorTextColor = inputFieldTextError,
                errorLabelColor = inputFieldLabelError,
                disabledBorderColor = inputFieldDefault,
                disabledTextColor = inputFieldTextDefault,
                disabledLabelColor = inputFieldLabelDefault
            ),
            leadingIcon = leadingIcons,
            trailingIcon = trailingIcons,
            enabled = isEnabled,
            supportingText = {
                if (isError && errorText.isNotEmpty()) {
                    TextComponent(text = errorText, maxLines = 2)
                }
            },
            // Remove the internal clickable behavior since it's handled by Box
            interactionSource = remember { MutableInteractionSource() }
        )

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldWidth.toDp() }) // Match the width of the OutlinedTextField
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    text = { TextComponent(text = itemContent(item)) }
                )
            }
        }
    }
}
