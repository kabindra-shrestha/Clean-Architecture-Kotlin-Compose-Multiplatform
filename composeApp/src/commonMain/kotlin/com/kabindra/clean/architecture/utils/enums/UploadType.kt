package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Photo
import androidx.compose.ui.graphics.vector.ImageVector

enum class UploadType(
    val title: String,
    val icon: ImageVector?,
    val slug: String,
) {
    Gallery(
        "Gallery",
        Icons.Default.Photo,
        "gallery",
    ),
    File(
        "File",
        Icons.Default.Description,
        "File",
    ),
}

inline fun <reified T : Enum<T>> getUploadType(slug: String): UploadType {
    return enumValues<T>().find { (it as UploadType).slug == slug } as UploadType
}