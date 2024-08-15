package com.kabindra.architecture

import NetworkConnectionAndroid
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NetworkConnectionAndroid.initAppContext(applicationContext)

        setContent {
            app()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    app()
}