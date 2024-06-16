package com.varunkumar.geminiapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.presentation.ChatScreen
import com.varunkumar.geminiapi.ui.theme.GeminiApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeminiApiTheme {
                ChatScreen(
                    modifier = Modifier
                    .fillMaxSize()
                )
            }
        }
    }
}