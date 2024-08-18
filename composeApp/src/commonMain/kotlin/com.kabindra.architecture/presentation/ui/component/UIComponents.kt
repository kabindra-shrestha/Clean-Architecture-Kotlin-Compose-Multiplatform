package presentation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun Lifecycle.observeAsSate(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsSate.addObserver(observer)
        onDispose {
            this@observeAsSate.removeObserver(observer)
        }
    }
    return state
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(title: String = "", iconButton: ImageVector, onClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(iconButton, contentDescription = "")
            }
        }
    )
}

@Composable
fun AlertDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    // below line is use to set our state
    // of dialog box to open as true.
    val openDialog = remember { mutableStateOf(true) }

    // below line is to check if the
    // dialog box is open or not.
    if (openDialog.value) {
        // below line is use to
        // display a alert dialog.
        AlertDialog(
            // on dialog dismiss we are setting
            // our dialog value to false.
            onDismissRequest = {
                /*openDialog.value = false

                onDismiss*/
            },

            // below line is use to display title of our dialog
            // box and we are setting text color to white.
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center
                )
            },

            // below line is use to display
            // description to our alert dialog.
            text = {
                Text(
                    text = message,
                    textAlign = TextAlign.Center
                )
            },

            // in below line we are displaying
            // our confirm button.
            confirmButton = {
                // below line we are adding on click
                // listener for our confirm button.
                TextButton(
                    onClick = {
                        openDialog.value = false

                        onConfirm()
                    }
                ) {
                    // in this line we are adding
                    // text for our confirm button.
                    Text("OK")
                }
            },
            // in below line we are displaying
            // our dismiss button.
            dismissButton = {
                // in below line we are displaying
                // our text button
                /*TextButton(
                    // adding on click listener for this button
                    onClick = {
                        openDialog.value = false

                        onDismiss
                    }
                ) {
                    // adding text to our button.
                    Text("Dismiss")
                }*/
            },
            // below line is use to add background color to our alert dialog
            // containerColor = Color.Magenta,

            // below line is use to add content color for our alert dialog.
            // contentColor = Color.White
        )
    }
}