package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerVector
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.utils.enums.UploadType

@Composable
fun ItemGalleryUpload(
    onClickGallery: () -> Unit,
    onClickFile: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = AppTheme.dimens.paddingNormal)
    ) {
        CardBorderInside(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .clickable { onClickGallery() },
            sides = listOf(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.paddingSmall)
                    .height(45.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                ImageHandlerVector(
                    modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                    image = UploadType.Gallery.icon,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextComponent(
                    text = "Gallery"
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        CardBorderInside(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .clickable {
                    onClickFile()
                },
            sides = listOf(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.paddingSmall)
                    .height(45.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                ImageHandlerVector(
                    modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                    image = UploadType.File.icon,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextComponent(
                    text = "File"
                )
            }
        }
    }

}
