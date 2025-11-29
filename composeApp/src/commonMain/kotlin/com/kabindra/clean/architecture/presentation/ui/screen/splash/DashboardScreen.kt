package com.kabindra.clean.architecture.presentation.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.utils.Connectivity
import com.kabindra.clean.architecture.utils.error.GlobalErrorDialog
import composemultiplatformcleanarchitecture.composeapp.generated.resources.Res
import composemultiplatformcleanarchitecture.composeapp.generated.resources.eight
import composemultiplatformcleanarchitecture.composeapp.generated.resources.five
import composemultiplatformcleanarchitecture.composeapp.generated.resources.four
import composemultiplatformcleanarchitecture.composeapp.generated.resources.nine
import composemultiplatformcleanarchitecture.composeapp.generated.resources.one
import composemultiplatformcleanarchitecture.composeapp.generated.resources.seven
import composemultiplatformcleanarchitecture.composeapp.generated.resources.six
import composemultiplatformcleanarchitecture.composeapp.generated.resources.ten
import composemultiplatformcleanarchitecture.composeapp.generated.resources.three
import composemultiplatformcleanarchitecture.composeapp.generated.resources.two
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardScreen(
    innerPadding: PaddingValues,
    onNavigateLogin: () -> Unit,
) {

    val connectivity = remember { Connectivity() }
    val isConnected by connectivity.isConnectedState.collectAsState()

    // Use DisposableEffect to reset states when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            // Reset the relevant states
        }
    }

    println("isConnected: $isConnected")
    if (!isConnected) {
        GlobalErrorDialog(
            isVisible = true,
            statusCode = -1,
            title = "No Network Connection",
            message = "Please check you internet connection.\nPlease try again.",
            onDismiss = {
            },
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalMultiBrowseCarouselExample()
        HorizontalUncontainedCarouselExample()
    }

}

data class CarouselItem(
    val id: Int,
    val imageResId: DrawableResource,
    val contentDescription: String
)

val carouselItems = listOf(
    CarouselItem(1, Res.drawable.one, "One"),
    CarouselItem(2, Res.drawable.two, "Two"),
    CarouselItem(3, Res.drawable.three, "Three"),
    CarouselItem(4, Res.drawable.four, "Four"),
    CarouselItem(5, Res.drawable.five, "Five"),
    CarouselItem(6, Res.drawable.six, "Six"),
    CarouselItem(7, Res.drawable.seven, "Seven"),
    CarouselItem(8, Res.drawable.eight, "Eight"),
    CarouselItem(9, Res.drawable.nine, "Nine"),
    CarouselItem(10, Res.drawable.ten, "Ten"),
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalMultiBrowseCarouselExample() {
    val items = remember { carouselItems }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]
        Image(
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge),
            painter = painterResource(resource = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalUncontainedCarouselExample() {
    val items = remember { carouselItems }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { carouselItems.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
        itemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = carouselItems[i]
        Image(
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge),
            painter = painterResource(resource = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}