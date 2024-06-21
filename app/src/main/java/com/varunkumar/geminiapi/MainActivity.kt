package com.varunkumar.geminiapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.varunkumar.geminiapi.presentation.Navigation
import com.varunkumar.geminiapi.presentation.screens.ChatScreen
import com.varunkumar.geminiapi.ui.theme.GeminiApiTheme
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO enable this later
//        enableEdgeToEdge()

        setContent {
            GeminiApiTheme {
                Navigation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}