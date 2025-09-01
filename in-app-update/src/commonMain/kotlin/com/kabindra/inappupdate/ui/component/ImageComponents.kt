package com.kabindra.inappupdate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.alexzhirkevich.compottie.LottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter

@Composable
fun ImageHandlerURL(
    modifier: Modifier = Modifier.width(250.dp).height(250.dp),
    image: String = "",
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        modifier = modifier,
        model = image,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}

@Composable
fun ImageHandlerVector(
    modifier: Modifier = Modifier.width(250.dp).height(250.dp),
    image: ImageVector?,
    contentDescription: String = "",
    tint: Color? = null,
    circular: Boolean = false,
    backgroundColor: Color = LocalContentColor.current,
    isClickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .then(
                if (circular)
                    modifier.clip(CircleShape).background(backgroundColor)
                else
                    modifier
            )
            .then(
                if (isClickable)
                    modifier.clickable { onClick() }
                else
                    modifier
            ),
        imageVector = image ?: Icons.Default.Image,
        contentDescription = contentDescription,
        tint = tint ?: LocalContentColor.current
    )
}

@Composable
fun ImageHandlerLottie(
    modifier: Modifier = Modifier.width(250.dp).height(250.dp),
    image: LottieComposition?,
    contentDescription: String = ""
) {
    Image(
        modifier = modifier,
        painter = rememberLottiePainter(composition = image),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit
    )
}