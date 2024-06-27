package com.varunkumar.geminiapi.presentation.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.varunkumar.geminiapi.presentation.HealthSensors
import com.varunkumar.geminiapi.presentation.features.home_feature.HomeState
import com.varunkumar.geminiapi.presentation.features.home_feature.HomeViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenseScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onDoneButtonClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = saveImageToExternalStorage(context, it)
            viewModel.onChangeImageUri(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onChangeImageUri(uri)
    }

    val shape = RoundedCornerShape(20.dp)
    val fModifier = Modifier.fillMaxWidth()

    Scaffold(
        modifier = modifier,
        containerColor = Color(0xffF2DBCE),
        topBar = {
            TopAppBar(
                modifier = fModifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "How you have felt today?",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 16.dp),
                containerColor = Color.Transparent
            ) {
                Button(
                    modifier = fModifier
                        .height(TextFieldDefaults.MinHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
//                        if (state.imageUri != null) {
                            viewModel.predictStress()
                            onDoneButtonClick()
//                        }
                    }
                ) {
                    Text(
                        text = "Predict",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TopImageDetectionBox(
                modifier = fModifier
                    .weight(0.6f)
                    .clip(shape)
                    .background(if (state.imageUri == null) Color.LightGray else Color.Black)
                    .clickable {
                        cameraLauncher.launch()
                    },
                state = state
            )

            BottomSliderBox(
                modifier = modifier
                    .weight(0.4f),
                viewModel = viewModel,
                state = state
            )
        }
    }
}

@Composable
private fun TopImageDetectionBox(
    modifier: Modifier = Modifier,
    state: HomeState
) {
    val fModifier = Modifier.fillMaxWidth()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (state.imageUri != null) {
            AsyncImage(
                modifier = fModifier,
                model = state.imageUri,
                contentDescription = "Image"
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    color = MaterialTheme.colorScheme.surface,
                    text = "Photo is necessary for stress evaluation.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun BottomSliderBox(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    state: HomeState
) {
    val fModifier = Modifier.fillMaxWidth()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomSlider(
                modifier = fModifier,
                viewModel = viewModel,
                sliderPosition = state.sensorValues.snoringRate,
                sensor = HealthSensors.SnoringRateSensors
            )

            CustomSlider(
                modifier = fModifier,
                viewModel = viewModel,
                sliderPosition = state.sensorValues.sleepHours,
                sensor = HealthSensors.HoursOfSleepSensors
            )

            CustomSlider(
                modifier = fModifier,
                viewModel = viewModel,
                sliderPosition = state.sensorValues.respirationRate,
                sensor = HealthSensors.RespirationRateSensors
            )
        }
    }
}

private fun saveImageToExternalStorage(context: Context, bitmap: Bitmap): Uri? {
    val filename = "${System.currentTimeMillis()}.png"
    var fos: OutputStream? = null
    var imageUri: Uri? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let {
                resolver.openOutputStream(it)
            }
        }
    } else {
        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
        imageUri = Uri.fromFile(image)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }

    return imageUri
}

@Composable
fun TimerBox(
    modifier: Modifier = Modifier,
    time: String
) {
    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = modifier
            .padding(end = 10.dp)
            .clip(shape)
            .background(Color.Red)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = time, color = MaterialTheme.colorScheme.primary)
    }
}