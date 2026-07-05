package com.kabindra.clean.architecture.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.CarouselItemScope
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import network.chaintech.sdpcomposemultiplatform.sdp

enum class ExpressiveCarouselVariant {
    MULTI_BROWSE,
    UNCONTAINED,
    CENTERED_HERO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExpressiveCarouselComponent(
    modifier: Modifier = Modifier,
    items: List<T>,
    variant: ExpressiveCarouselVariant = ExpressiveCarouselVariant.MULTI_BROWSE,
    preferredItemWidth: Dp = 112.sdp,
    itemWidth: Dp = 112.sdp,
    maxItemWidth: Dp = Dp.Unspecified,
    itemSpacing: Dp = 5.sdp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.sdp),
    userScrollEnabled: Boolean = true,
    itemContent: @Composable CarouselItemScope.(item: T, index: Int) -> Unit,
) {
    if (items.isEmpty()) return

    val state = rememberCarouselState { items.size }

    when (variant) {
        ExpressiveCarouselVariant.MULTI_BROWSE -> {
            HorizontalMultiBrowseCarousel(
                state = state,
                modifier = modifier,
                preferredItemWidth = preferredItemWidth,
                itemSpacing = itemSpacing,
                contentPadding = contentPadding,
                userScrollEnabled = userScrollEnabled,
            ) { index ->
                itemContent(items[index], index)
            }
        }

        ExpressiveCarouselVariant.UNCONTAINED -> {
            HorizontalUncontainedCarousel(
                state = state,
                modifier = modifier,
                itemWidth = itemWidth,
                itemSpacing = itemSpacing,
                contentPadding = contentPadding,
                userScrollEnabled = userScrollEnabled,
            ) { index ->
                itemContent(items[index], index)
            }
        }

        ExpressiveCarouselVariant.CENTERED_HERO -> {
            HorizontalCenteredHeroCarousel(
                state = state,
                modifier = modifier,
                maxItemWidth = maxItemWidth,
                itemSpacing = itemSpacing,
                contentPadding = contentPadding,
                userScrollEnabled = userScrollEnabled,
            ) { index ->
                itemContent(items[index], index)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpressiveTextCarousel(
    modifier: Modifier = Modifier,
    items: List<String>,
    variant: ExpressiveCarouselVariant = ExpressiveCarouselVariant.MULTI_BROWSE,
    onItemClick: (String) -> Unit = {},
) {
    ExpressiveCarouselComponent(
        modifier = modifier,
        items = items,
        variant = variant,
    ) { item, index ->
        CardComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.sdp)
                .maskClip(MaterialTheme.shapes.extraLarge)
                .clickable { onItemClick(item) },
            variant = CardVariant.FILLED,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.sdp),
                verticalArrangement = Arrangement.spacedBy(5.sdp),
            ) {
                TextComponent(
                    text = item,
                    type = TextType.Title,
                    size = TextSize.Medium,
                )
                TextComponent(
                    text = "Expressive carousel item #${index + 1}",
                    type = TextType.Body,
                    size = TextSize.Small,
                    maxLines = 2,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpressiveImageCarousel(
    modifier: Modifier = Modifier,
    imageUrls: List<String>,
    variant: ExpressiveCarouselVariant = ExpressiveCarouselVariant.MULTI_BROWSE,
    onImageClick: (String) -> Unit = {},
) {
    ExpressiveCarouselComponent(
        modifier = modifier,
        items = imageUrls,
        variant = variant,
    ) { imageUrl, index ->
        CardComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.sdp)
                .maskClip(MaterialTheme.shapes.extraLarge)
                .clickable { onImageClick(imageUrl) },
            variant = CardVariant.ELEVATED,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                ImageHandlerURL(
                    modifier = Modifier.fillMaxSize(),
                    image = imageUrl,
                    contentDescription = "Carousel image ${index + 1}",
                    contentScale = ContentScale.Crop,
                )
                TextComponent(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(7.sdp),
                    text = "Image #${index + 1}",
                    type = TextType.Label,
                    size = TextSize.Small,
                )
            }
        }
    }
}
