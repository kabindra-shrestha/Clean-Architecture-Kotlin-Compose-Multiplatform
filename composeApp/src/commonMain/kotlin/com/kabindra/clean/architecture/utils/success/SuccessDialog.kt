@file:OptIn(ExperimentalResourceApi::class)

package com.kabindra.clean.architecture.utils.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kabindra.clean.architecture.presentation.ui.component.ButtonText
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerLottie
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import composemultiplatformcleanarchitecture.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun GlobalSuccessDialog(
    isVisible: Boolean = false,
    isAction: Boolean = false,
    message: String,
    onDismiss: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(isVisible) }

    // Automatically dismiss the dialog after 5 seconds
    LaunchedEffect(openDialog.value) {
        if (openDialog.value) {
            delay(5000) // 5 seconds delay

            openDialog.value = false

            onDismiss()
        }
    }

    if (openDialog.value) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/success.json").decodeToString()
            )
        }

        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.paddingSmall),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(AppTheme.dimens.paddingSmall)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageHandlerLottie(
                        modifier = Modifier.width(100.dp).height(100.dp),
                        image = composition,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = message,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (isAction) {
                        Row(
                            modifier = Modifier
                                .padding(AppTheme.dimens.paddingSmall)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonText(
                                modifier = Modifier.align(Alignment.CenterVertically).width(100.dp),
                                text = "Ok",
                                onClick = {
                                    openDialog.value = false

                                    onDismiss()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}