package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.presentation.viewModels.AppViewModel
import com.varunkumar.geminiapi.presentation.viewModels.SenseViewModel
import com.varunkumar.geminiapi.ui.theme.GeminiApiTheme
import com.varunkumar.geminiapi.ui.theme.secondary

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SenseViewModel,
    appViewModel: AppViewModel
) {

    GeminiApiTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.primary,
            bottomBar = {
                CustomBottomNavigation(
                    navController = navController,
                    appViewModel = appViewModel
                )
            }
        ) {
            Column(
                modifier = modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ZoomInOutBoxAnimation {
                    navController.navigate(Routes.Welcome.route)
                }
            }
        }
    }
}

@Composable
fun RecommendTab(modifier: Modifier = Modifier) {

}


@Composable
fun ZoomInOutBoxAnimation(
    modifier: Modifier = Modifier,
    onScanButtonClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ScaleBox")
    val scaleAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f, // Adjust the zoom factor as needed
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(scaleAnimation)
                .clip(CircleShape)
                .background(secondary)
                .clickable { onScanButtonClick() }
        )
        Text(text = "Scan", style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
private fun PreHome() {
    ZoomInOutBoxAnimation() {

    }
}