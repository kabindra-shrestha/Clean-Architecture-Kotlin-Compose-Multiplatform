package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.ui.graphics.vector.ImageVector
import io.ktor.http.ContentType

enum class FileExtensionType(
    val title: String,
    val extension: String,
    val contentType: String,
    val icon: ImageVector
) {
    JPG("JPG Image", "jpg", ContentType.Image.JPEG.toString(), Icons.Default.Description),
    JPEG("JPEG Image", "jpeg", ContentType.Image.JPEG.toString(), Icons.Default.Description),
    PNG("PNG Image", "png", ContentType.Image.PNG.toString(), Icons.Default.Description),
    PDF("PDF Document", "pdf", ContentType.Application.Pdf.toString(), Icons.Default.PictureAsPdf),
    DOC(
        "Word Document",
        "doc",
        ContentType.Application.Docx.toString(),
        Icons.Default.Description
    ),
    DOCX(
        "Word Document Docx",
        "docx",
        ContentType.Application.Docx.toString(),
        Icons.Default.Description
    ),

}

inline fun <reified T : Enum<T>> getFileExtensionType(extension: String): FileExtensionType {
    return enumValues<T>().find { (it as FileExtensionType).extension == extension } as FileExtensionType
}