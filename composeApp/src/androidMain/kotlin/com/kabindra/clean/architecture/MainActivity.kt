package com.kabindra.clean.architecture

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kabindra.clean.architecture.utils.handleNavigationRedirection
import com.kabindra.inappupdate.initializeUpdateManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        println("Firebase handleIntent onCreate")
        handleIntent(intent)

        initializeUpdateManager(this)

        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle the new intent (in case the activity is already running)
        println("Firebase handleIntent onNewIntent")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.extras?.let { extras ->
            val type = extras.getString("type") ?: ""
            val ticket_id = extras.getString("ticket_id") ?: ""
            val workflow = extras.getString("workflow") ?: ""
            val date = extras.getString("date") ?: ""

            if (type.isNotEmpty()) {
                handleNavigationRedirection(
                    type = type,
                    ticket_id = ticket_id,
                    workflow = workflow,
                    date = date
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}