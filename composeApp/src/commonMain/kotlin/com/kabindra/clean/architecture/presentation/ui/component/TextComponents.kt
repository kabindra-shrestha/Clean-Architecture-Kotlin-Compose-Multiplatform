package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.kabindra.clean.architecture.presentation.ui.theme.inputFieldTextError

enum class TextType { Display, Headline, Title, Body, Label }
enum class TextSize { Small, Medium, Large }

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: Any,
    type: TextType = TextType.Body,
    size: TextSize = TextSize.Large,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    val style = when (type) {
        TextType.Display -> when (size) {
            TextSize.Small -> MaterialTheme.typography.displaySmall
            TextSize.Medium -> MaterialTheme.typography.displayMedium
            TextSize.Large -> MaterialTheme.typography.displayLarge
        }

        TextType.Headline -> when (size) {
            TextSize.Small -> MaterialTheme.typography.headlineSmall
            TextSize.Medium -> MaterialTheme.typography.headlineMedium
            TextSize.Large -> MaterialTheme.typography.headlineLarge
        }

        TextType.Title -> when (size) {
            TextSize.Small -> MaterialTheme.typography.titleSmall
            TextSize.Medium -> MaterialTheme.typography.titleMedium
            TextSize.Large -> MaterialTheme.typography.titleLarge
        }

        TextType.Body -> when (size) {
            TextSize.Small -> MaterialTheme.typography.bodySmall
            TextSize.Medium -> MaterialTheme.typography.bodyMedium
            TextSize.Large -> MaterialTheme.typography.bodyLarge
        }

        TextType.Label -> when (size) {
            TextSize.Small -> MaterialTheme.typography.labelSmall
            TextSize.Medium -> MaterialTheme.typography.labelMedium
            TextSize.Large -> MaterialTheme.typography.labelLarge
        }
    }
    when (text) {
        is String -> {
            Text(
                modifier = modifier,
                text = text,
                textAlign = textAlign,
                style = style.copy(
                    fontWeight = fontWeight,
                    color = color ?: style.color,
                    background = background ?: style.background
                ),
                maxLines = maxLines,
                overflow = overflow
            )
        }

        is AnnotatedString -> {
            Text(
                modifier = modifier,
                text = text,
                textAlign = textAlign,
                style = style.copy(
                    fontWeight = fontWeight,
                    color = color ?: style.color,
                    background = background ?: style.background
                ),
                maxLines = maxLines,
                overflow = overflow
            )
        }

        else -> throw IllegalArgumentException("Unsupported text type")
    }
}


@Composable
fun TextSmall(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextMedium(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextLargeOtp(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextTitleSmall(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextTitleMedium(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextTitleMedium(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextTitleLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextTitleLargeOtp(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

/*@Composable
fun TextTitleLarge(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}*/
@Composable
fun TextTitleLarge(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(fontWeight = fontWeight),
        maxLines = maxLines,
        overflow = overflow
    )
}


@Composable
fun TextHeadlineSmall(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextError(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color? = inputFieldTextError,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TextButtonAction(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color? = null,
    background: Color? = null
) {
    Text(
        modifier = modifier,
        text = text,
        style = style.copy(
            fontWeight = fontWeight,
            color = color ?: style.color,
            background = background ?: style.background
        )
    )
}