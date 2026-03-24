package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class CardVariant {
    FILLED,
    ELEVATED,
    OUTLINED
}

@Composable
fun CardComponent(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.ELEVATED,
    useExpressive: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val resolvedVariant = if (useExpressive) variant else CardVariant.FILLED

    when (resolvedVariant) {
        CardVariant.FILLED -> {
            Card(
                modifier = modifier,
                content = content
            )
        }

        CardVariant.ELEVATED -> {
            ElevatedCard(
                modifier = modifier,
                content = content
            )
        }

        CardVariant.OUTLINED -> {
            OutlinedCard(
                modifier = modifier,
                content = content
            )
        }
    }
}
