package com.varunkumar.geminiapi.presentation.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.varunkumar.geminiapi.presentation.HealthSensors
import com.varunkumar.geminiapi.presentation.viewModels.SenseViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenseScreen(
    modifier: Modifier = Modifier,
    senseViewModel: SenseViewModel,
    onDoneButtonClick: () -> Unit
) {
    val state by senseViewModel.state.collectAsState()
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = saveImageToExternalStorage(context, it)
            senseViewModel.onChangeImageUri(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        senseViewModel.onChangeImageUri(uri)
    }

    val shape = RoundedCornerShape(20.dp)
    val bgColor = Color(0xffF2DBCE)
    val fModifier = Modifier.fillMaxWidth()

    Scaffold(
        modifier = modifier,
        containerColor = bgColor,
        topBar = {
            TopAppBar(
                modifier = fModifier,
                colors = TopAppBarDefaults.largeTopAppBarColors(
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
                contentPadding = PaddingValues(horizontal = 10.dp),
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
                        senseViewModel.predictStress()
                        onDoneButtonClick()
                    }
                ) {
                    Text(
                        text = "Next",
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
            Box(
                modifier = fModifier
                    .weight(0.7f)
                    .clip(shape)
                    .background(if (state.imageUri == null) Color.LightGray else Color.Black)
                    .clickable {
                        cameraLauncher.launch()
                    },
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

            Box(
                modifier = modifier
                    .weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
//                    Row(
//                        modifier = fModifier,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "Extremely Negative",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        Text(
//                            text = "Neutral",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        Text(
//                            text = "Extremely Positive",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        CustomSlider(
                            modifier = fModifier,
                            viewModel = senseViewModel,
                            sliderPosition = state.snoringRate,
                            sensor = HealthSensors.SnoringRateSensors
                        )

                        CustomSlider(
                            modifier = fModifier,
                            viewModel = senseViewModel,
                            sliderPosition = state.respirationRate,
                            sensor = HealthSensors.RespirationRateSensors
                        )
                    }
                }
            }
        }

//        Column(
//            modifier = modifier
//                .padding(16.dp)
//                .padding(it)
//        ) {
//            AnimatedVisibility(
//                visible = state.isImageScreen,
//                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
//                exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
//            ) {
//                ImageDetectScreen(
//                    modifier = modifier,
//                    gradient = gradient,
//                    onNextButtonClick = senseViewModel::onScreenChange
//                )
//            }
//
//            AnimatedVisibility(
//                visible = !state.isImageScreen,
//                enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
//                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
//            ) {
//                SliderScreen(
//                    modifier = modifier,
//                    viewModel = senseViewModel
//                )
//            }
//        }
    }
}

//private fun saveImageToExternalStorage(context: Context, bitmap: Bitmap): Uri? {
//    val filename = "${System.currentTimeMillis()}.jpg"
//    var fos: OutputStream? = null
//    var imageUri: Uri? = null
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        context.contentResolver?.also { resolver ->
//            val contentValues = ContentValues().apply {
//                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//            }
//            imageUri =
//                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//            fos = imageUri?.let {
//                resolver.openOutputStream(it)
//            }
//        }
//    } else {
//        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val image = File(imagesDir, filename)
//        fos = FileOutputStream(image)
//        imageUri = Uri.fromFile(image)
//    }
//
//    fos?.use {
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//    }
//
//    return imageUri
//}

private fun saveImageToExternalStorage(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val filesDir = context.filesDir
        val file = File(filesDir, "image.jpg") // Create a file to store the image
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Save the bitmap to the file
        outputStream.flush()
        outputStream.close()
        val result = FileProvider.getUriForFile(
            context,
            "com.varunkumar.geminiapi.fileprovider", // Replace with your app's authority
            file
        )
        Log.d("image", "Error saving image: ${result.toString()}")
        result
    } catch (e: Exception) {
        // Handle exceptions, e.g., file creation or saving errors
        Log.d("image", "Error saving image: ${e.message}")
        null
    }
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