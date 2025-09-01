package com.kabindra.clean.architecture.presentation.ui.component.filepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.EditLeaveDocument
import com.kabindra.clean.architecture.domain.entity.EditTimeDocument
import com.kabindra.clean.architecture.presentation.ui.component.BaseLazy
import com.kabindra.clean.architecture.presentation.ui.component.LazyListType
import com.kabindra.clean.architecture.presentation.ui.component.LazyScrollDirection
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextError
import com.kabindra.clean.architecture.presentation.ui.component.TextMedium
import com.kabindra.clean.architecture.presentation.ui.items.ExistingTimeUploadedFileItem
import com.kabindra.clean.architecture.presentation.ui.items.ExistingUploadedFileItem
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme

@Composable
fun ExistingLeaveFileUploaded(
    modifier: Modifier = Modifier,
    existingDocuments: List<EditLeaveDocument>,
    onDocumentsChanged: (EditLeaveDocument) -> Unit,
    existingDocumentFieldError: String? = null,
) {
    if (existingDocuments.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedCard(
            modifier = modifier
                .fillMaxWidth()
        ) {
            TextComponent(
                text = "Documents",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BaseLazy(
                    modifier = Modifier.wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    arrangement = Arrangement.spacedBy(8.dp),
                    items = existingDocuments.toList(),
                    listType = LazyListType.LIST,
                    scrollDirection = LazyScrollDirection.HORIZONTAL,
                    itemContent = { index, item ->
                        ExistingUploadedFileItem(
                            item = item,
                            onRemove = { onDocumentsChanged(item) },
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

        existingDocumentFieldError?.let {
            TextError(
                text = it,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingNormal)
            )
        }

    }
}

@Composable
fun ExistingTimeFileUploaded(
    modifier: Modifier = Modifier,
    existingDocuments: List<EditTimeDocument>,
    onDocumentsChanged: (EditTimeDocument) -> Unit,
    existingDocumentFieldError: String? = null,
) {
    if (existingDocuments.isNotEmpty()) {
        OutlinedCard(
            modifier = modifier
                .fillMaxWidth()
        ) {
            TextMedium(
                text = "Documents",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BaseLazy(
                    modifier = Modifier.wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    arrangement = Arrangement.spacedBy(8.dp),
                    items = existingDocuments.toList(),
                    listType = LazyListType.LIST,
                    scrollDirection = LazyScrollDirection.HORIZONTAL,
                    itemContent = { index, item ->
                        ExistingTimeUploadedFileItem(
                            item = item,
                            onRemove = { onDocumentsChanged(item) },
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

        existingDocumentFieldError?.let {
            TextError(
                text = it,
                modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingNormal)
            )
        }

    }
}

