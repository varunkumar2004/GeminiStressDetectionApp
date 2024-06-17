package com.varunkumar.geminiapi.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun getScreenResolutionDp(): Pair<Dp, Dp> {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    return Pair(
        screenWidthDp,
        screenHeightDp
    )
}

fun generateRandomColor(): Color {
    return Color(
        red = Random.nextInt(128) + 128, // Range from 128 to 255
        green = Random.nextInt(128) + 128,
        blue = Random.nextInt(128) + 128
    )
}