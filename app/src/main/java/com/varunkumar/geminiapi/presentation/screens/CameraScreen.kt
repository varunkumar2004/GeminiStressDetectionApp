package com.varunkumar.geminiapi.presentation.screens

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    val fModifier = Modifier.fillMaxWidth()
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp),
            onClick = { /*TODO*/ }
        ) {
            Icon(imageVector = Icons.Outlined.Cameraswitch, contentDescription = null)
        }

//        Column(
//            modifier = modifier,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                imageVector = Icons.Default.AddAPhoto,
//                tint = MaterialTheme.colorScheme.surface,
//                contentDescription = null
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                color = MaterialTheme.colorScheme.surface,
//                text = "Photo is necessary for stress evaluation.",
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }

        Box(modifier = modifier) {
            CameraPreview(controller = controller, modifier)
        }

        Box(
            modifier = fModifier
                .padding(vertical = 10.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clickable {

                    }
            )
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifeCycleOwner)
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CameraPrev() {
    CameraScreen(modifier = Modifier.fillMaxSize())
}