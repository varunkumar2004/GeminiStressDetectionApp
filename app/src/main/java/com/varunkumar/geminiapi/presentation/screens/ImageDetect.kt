package com.varunkumar.geminiapi.presentation.screens

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun ImageDetectScreen(
    modifier: Modifier = Modifier,
    gradient: Brush,
    onNextButtonClick: () -> Unit
) {
    val fModifier = Modifier.fillMaxWidth()
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    )
    val shape = RoundedCornerShape(40.dp)

    val context = LocalContext.current
//    val file = createImageFile(context)
//    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }
//
//    val cameraLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
//            if (success) {
//                imageUri = uri
//            }
//        }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        imageUri = it
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
//        if (isGranted) cameraLauncher.launch(uri)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = fModifier
                .aspectRatio(1f)
                .clip(shape)
                .background(MaterialTheme.colorScheme.secondary)
                .clickable {
                },
            contentAlignment = Alignment.Center
        ) {
//            if (imageUri != null) {
//                AsyncImage(
//                    modifier = fModifier,
//                    model = imageUri,
//                    contentDescription = "Image"
//                )
//            } else {
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
//                }
            }
        }

        Row(
            modifier = fModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
//            Button(
//                colors = buttonColors,
//                modifier = fModifier
//                    .weight(1f)
//                    .height(TextFieldDefaults.MinHeight),
//                onClick = {
////                    if (imageUri == null) {
//                        if (ContextCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.CAMERA
//                            ) == PackageManager.PERMISSION_GRANTED
//                        ) {
////                        cameraLauncher.launch(uri)
//                        } else {
//                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//                        }
//                    } else
//                    onNextButtonClick()
//                }
//            ) {
//                Text(
//                    text = if (imageUri == null) "Take Photo" else "Next",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold
//                )
//            }

//            IconButton(
//                modifier = Modifier,
//                onClick = {
//                    galleryLauncher.launch(
//                        PickVisualMediaRequest(
//                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
//                        )
//                    )
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.PhotoLibrary,
//                    tint = MaterialTheme.colorScheme.tertiary,
//                    contentDescription = null
//                )
//            }
        }
    }
}

fun createImageFile(context: Context): File {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "image",
        ".jpg"
    )
}

@Preview
@Composable
private fun ImagePrev() {
    ImageDetectScreen(
        modifier = Modifier.fillMaxSize(),
        gradient = Brush.customRadialGradient(
            colors = listOf(
                Color(0xffFFCC9C),
                Color(0xffFFA16C)
            )
        )
    ) {}
}