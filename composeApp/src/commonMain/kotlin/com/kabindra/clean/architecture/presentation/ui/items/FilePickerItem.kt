package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.EditLeaveDocument
import com.kabindra.clean.architecture.domain.entity.EditTimeDocument
import com.kabindra.clean.architecture.presentation.ui.component.ButtonClose
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.theme.cardBorder
import com.kabindra.clean.architecture.utils.enums.FileExtensionType
import com.kabindra.clean.architecture.utils.enums.getFileExtensionType
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun FilePickerItem(
    item: PlatformFile,
    modifier: Modifier = Modifier,
    onRemove: (PlatformFile) -> Unit,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val snackBarHostState: SnackbarHostState = koinInject()
    val scope = rememberCoroutineScope()
    val fileExtensionType = getFileExtensionType<FileExtensionType>(item.extension.lowercase())

    if (fileExtensionType != null) {
        Box(
            modifier = modifier
                .size(width = 100.dp, height = 100.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) cardBorder else cardBorder
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() }
            ) {
                ImageHandlerURL(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    image = item.path,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    placeholder = fileExtensionType.icon
                )
            }
            ButtonClose(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(2.dp),
                onClick = { onRemove(item) }
            )
        }
    } else {
        scope.launch {
            snackBarHostState.showSnackbar(message = "Invalid file extension")
        }
    }
}

@Composable
fun ExistingUploadedFileItem(
    item: EditLeaveDocument,
    modifier: Modifier = Modifier,
    onRemove: (EditLeaveDocument) -> Unit,
    isSelected: Boolean
) {
    val snackBarHostState: SnackbarHostState = koinInject()
    val scope = rememberCoroutineScope()
    val fileExtensionType = getFileExtensionType<FileExtensionType>(
        item.extension?.lowercase() ?: ""
    )

    if (fileExtensionType != null) {
        Box(
            modifier = modifier
                .size(width = 100.dp, height = 100.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) cardBorder else cardBorder
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ImageHandlerURL(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    image = item.path.takeIf { !it.isNullOrBlank() } ?: "",
                    contentDescription = item.name.takeIf { !it.isNullOrBlank() } ?: "",
                    contentScale = ContentScale.Crop,
                    placeholder = fileExtensionType.icon
                )

            }
            if (item.canRemove == true) {
                ButtonClose(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp),
                    onClick = {
                        onRemove(item)
                    }
                )
            }
        }
    } else {
        scope.launch {
            snackBarHostState.showSnackbar(message = "Invalid file extension")
        }
    }
}


@Composable
fun ExistingTimeUploadedFileItem(
    item: EditTimeDocument,
    modifier: Modifier = Modifier,
    onRemove: (EditTimeDocument) -> Unit,
    isSelected: Boolean
) {
    val snackBarHostState: SnackbarHostState = koinInject()
    val scope = rememberCoroutineScope()

    val fileExtensionType = getFileExtensionType<FileExtensionType>(
        item.extension?.takeIf { it.isNotEmpty() }?.lowercase() ?: ""
    )


    if (fileExtensionType != null) {
        Box(
            modifier = modifier
                .size(width = 100.dp, height = 100.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) cardBorder else cardBorder
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ImageHandlerURL(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    image = item.path.takeIf { !it.isNullOrEmpty() } ?: "",
                    contentDescription = item.name.takeIf { !it.isNullOrEmpty() } ?: "",
                    contentScale = ContentScale.Crop,
                    placeholder = fileExtensionType.icon
                )

            }
            if (item.canRemove == true) {
                ButtonClose(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp),
                    onClick = {
                        onRemove(item)
                    }
                )
            }
        }
    } else {
        scope.launch {
            snackBarHostState.showSnackbar(message = "Invalid file extension")
        }
    }
}