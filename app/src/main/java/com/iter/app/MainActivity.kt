package com.iter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.iter.app.tracking.InteractionLog
import com.iter.app.ui.navigation.AppNavHost
import com.iter.app.ui.theme.IterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IterTheme {
                Scaffold { padding -> AppNavHost(Modifier.padding(padding)) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        InteractionLog.record(InteractionLog.Event.AppOpened)
    }
}
