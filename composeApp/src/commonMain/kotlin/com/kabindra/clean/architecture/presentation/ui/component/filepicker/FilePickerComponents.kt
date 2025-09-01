package com.kabindra.clean.architecture.presentation.ui.component.filepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.ButtonTextAndIcon
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.ModalBottomSheetComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextError
import com.kabindra.clean.architecture.presentation.ui.items.FilePickerItem
import com.kabindra.clean.architecture.presentation.ui.items.ItemGalleryUpload
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.utils.enums.FileExtensionType
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher

@Composable
fun FilePicker(
    modifier: Modifier = Modifier,
    documents: Set<PlatformFile>,
    onDocumentsChanged: (Set<PlatformFile>) -> Unit,
    documentFieldError: String? = null,
) {
    var isSheetVisible by remember { mutableStateOf(false) }
    val directory: PlatformFile? by remember { mutableStateOf(null) }

    val imagePickerLauncher = rememberFilePickerLauncher(
        type = FileKitType.Image,
        mode = FileKitMode.Multiple(),
        directory = directory,
        title = "Pick Images",
        onResult = { files ->
            files?.let { onDocumentsChanged(documents + it) }
            isSheetVisible = false
        }
    )

    val documentPickerLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(
            listOf(
                FileExtensionType.DOC.extension,
                FileExtensionType.DOCX.extension,
                FileExtensionType.PDF.extension
            )
        ),
        mode = FileKitMode.Multiple(),
        title = "Pick Documents",
        directory = directory,
        onResult = { files ->
            files?.let { onDocumentsChanged(documents + it) }
            isSheetVisible = false
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    CardBorderInside(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        sides = listOf(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                /*   ImageHandlerVector(
                       modifier = Modifier.size(24.dp),
                       image = Icons.Default.CloudUpload,
                       contentDescription = "Browse files",
                   )
                   TextComponent(text = "Upload", fontWeight = FontWeight.Bold)*/

                ButtonTextAndIcon(
                    modifier = Modifier,
                    text = "Upload",
                    iconVector = Icons.Default.Upload,
                    iconContentDescription = "Upload",
                    onClick = {
                        isSheetVisible = true
                        /* scope.launch { sheetState.hide() }.invokeOnCompletion {
                             if (!sheetState.isVisible) {
                                 onDismissRequest()
                             }
                         }*/
                    }
                )

                BaseLazy(
                    modifier = Modifier.wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    arrangement = Arrangement.spacedBy(8.dp),
                    items = documents.toList(),
                    listType = LazyListType.LIST,
                    scrollDirection = LazyScrollDirection.HORIZONTAL,
                    itemContent = { index, item ->
                        FilePickerItem(
                            item = item,
                            onRemove = { onDocumentsChanged(documents - it) },
                            onClick = {},
                            isSelected = false
                        )
                    },
                    isLoading = false,
                    loadingContent = {},
                    onLoadMore = {},
                    onScrollStateChanged = {},
                )
            }
        }
    }

    documentFieldError?.let {
        TextError(
            text = it,
            modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingNormal)
        )
    }

    ModalBottomSheetComponent(
        modifier = Modifier.padding(
            AppTheme.dimens.paddingSmall
        ),
        title = "Select your option to upload",
        isVisible = isSheetVisible,
        icon = Icons.Default.Close,
        onDismiss = {
            isSheetVisible = false
        },
        content = {
            ItemGalleryUpload(
                onClickGallery = { imagePickerLauncher.launch() },
                onClickFile = { documentPickerLauncher.launch() }
            )
        }
    )
}

