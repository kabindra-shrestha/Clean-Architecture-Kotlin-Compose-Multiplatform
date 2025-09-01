package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.presentation.ui.theme.divider
import com.kabindra.clean.architecture.presentation.ui.theme.qrScannerBorder
import com.kabindra.clean.architecture.presentation.ui.theme.qrScannerOverlay
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@Composable
fun QrScannerScreenComponent(
    onCompletion: (String) -> Unit,
    onBackNavigate: () -> Unit
) {
    var flashlightOn by remember { mutableStateOf(false) }
    var openImagePicker by remember { mutableStateOf(false) }

    var isScanning by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            QrScannerBox(
                flashlightOn = flashlightOn,
                openImagePicker = openImagePicker,
                isScanning = isScanning,
                onFlashToggle = { flashlightOn = !flashlightOn },
                onImagePickerOpen = {
                    openImagePicker = true
                },
                onGalleryOpened = {
                    openImagePicker = false // Reset after opening gallery
                },
                onCompletion = {
                    if (isScanning) {
                        isScanning = false

                        onCompletion(it)
                    }
                }
            )
        }

        TopAppBarWithBackComponent(
            title = "",
            onBackNavigate = { onBackNavigate() })

        FlashAndGalleryControls(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            onFlashToggle = { flashlightOn = !flashlightOn },
            onImagePickerOpen = { openImagePicker = true }
        )

        /*Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            QRCodeOverlay(qrCodeURL, clipboardManager)
        }*/
    }
}

@Composable
fun QrScannerBox(
    flashlightOn: Boolean,
    openImagePicker: Boolean,
    isScanning: Boolean,
    onFlashToggle: () -> Unit,
    onImagePickerOpen: () -> Unit,
    onGalleryOpened: () -> Unit, // New callback to handle gallery state reset
    onCompletion: (String) -> Unit
) {
    var overlayShape by remember { mutableStateOf(OverlayShape.Square) }
    var cameraLens by remember { mutableStateOf(CameraLens.Back) }

    if (isScanning) {
        QrScanner(
            modifier = Modifier,
            flashlightOn = flashlightOn,
            cameraLens = cameraLens,
            openImagePicker = openImagePicker,
            onCompletion = { onCompletion(it) },
            imagePickerHandler = {
                onGalleryOpened()
            },
            onFailure = {},
            overlayShape = overlayShape,
            overlayColor = qrScannerOverlay,
            overlayBorderColor = qrScannerBorder,
        )
    }
}

@Composable
fun FlashAndGalleryControls(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    onFlashToggle: () -> Unit,
    onImagePickerOpen: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(10.dp),
        contentAlignment = contentAlignment
    ) {
        Row(
            modifier = Modifier
                .offset(y = 150.dp)
                .clip(
                    shape = RoundedCornerShape(24.dp)
                )
                .width(100.dp)
                .height(40.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ImageHandlerVector(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onFlashToggle),
                image = Icons.Default.FlashOn,
                contentDescription = "Flashlight",
                circular = true
            )
            VerticalDivider(thickness = 1.dp, color = divider)
            ImageHandlerVector(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onImagePickerOpen),
                image = Icons.Default.CopyAll,
                contentDescription = "Gallery",
                circular = true
            )
        }
    }
}

@Composable
fun QRCodeOverlay(qrCodeURL: String, clipboardManager: ClipboardManager) {
    if (qrCodeURL.isNotEmpty()) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextSmall(
                modifier = Modifier.fillMaxWidth(),
                text = qrCodeURL,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            ImageHandlerVector(
                modifier = Modifier.clickable {
                    clipboardManager.setText(AnnotatedString(qrCodeURL))
                },
                image = Icons.Default.CopyAll,
                contentDescription = "Copy"
            )
        }
    }
}