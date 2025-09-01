package com.kabindra.clean.architecture.presentation.ui.component.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerRes
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.component.TextMedium
import com.kabindra.clean.architecture.presentation.ui.component.TextTitleSmall
import com.kabindra.clean.architecture.presentation.ui.items.DrawerItem
import com.kabindra.clean.architecture.presentation.ui.theme.drawerBackground
import com.kabindra.clean.architecture.presentation.ui.theme.onPrimaryLight
import com.kabindra.clean.architecture.utils.enums.MenuType
import composemultiplatformcleanarchitecture.composeapp.generated.resources.Res
import composemultiplatformcleanarchitecture.composeapp.generated.resources.splash_icon

@Composable
fun DrawerComponents(
    modifier: Modifier = Modifier,
    userData: User?,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(drawerBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            DrawerHeaderSection(userData)
        }

        DrawerItemsSection(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                onClick(it)
            }
        )

        DrawerFooter(
            modifier = Modifier.fillMaxWidth().padding(10.dp).align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CbBackgroundImage() {
    ImageHandlerRes(
        modifier = Modifier.fillMaxSize(),
        image = Res.drawable.splash_icon,
        contentDescription = "CB Background",
    )
}

@Composable
fun DrawerHeaderSection(userData: User?) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            ImageHandlerURL(
                modifier = Modifier.size(32.dp),
                image = userData?.profile_picture ?: "",
                contentDescription = "Profile Icon",
                contentScale = ContentScale.Crop,
                placeholder = Icons.Default.Person,
                circular = true
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            TextTitleSmall(text = "Welcome", color = onPrimaryLight)
            TextTitleSmall(text = userData?.name ?: "", color = onPrimaryLight)
        }
    }
}

@Composable
fun DrawerItemsSection(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    var selectedRoute by rememberSaveable { mutableStateOf(MenuType.Home.slug) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        MenuType.entries
            .filter { it.isDrawer }
            .forEachIndexed { index, label ->
                DrawerItem(
                    label = label,
                    selected = selectedRoute == label.slug,
                    onItemClick = {
                        selectedRoute = label.slug

                        onClick(selectedRoute)
                    }
                )
            }
    }
}

@Composable
fun DrawerFooter(modifier: Modifier = Modifier) {
    TextMedium(
        modifier = modifier,
        text = "Code Bright\nV1.0.0",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
        maxLines = 2,
        color = onPrimaryLight
    )
}
