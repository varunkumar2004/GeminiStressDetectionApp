package com.varunkumar.geminiapi

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.varunkumar.geminiapi.presentation.Navigation
import com.varunkumar.geminiapi.presentation.screens.ChatScreen
import com.varunkumar.geminiapi.ui.theme.GeminiApiTheme
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO enable this later
        enableEdgeToEdge()

        if (!hasRequiredPermission()) {
            ActivityCompat.requestPermissions(this, permissions, 0)
        }

        setContent {
            GeminiApiTheme {
                Navigation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun hasRequiredPermission(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val permissions = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}