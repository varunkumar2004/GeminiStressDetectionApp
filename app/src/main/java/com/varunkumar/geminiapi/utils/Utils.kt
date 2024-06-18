package com.varunkumar.geminiapi.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Clock
import java.util.Date
import java.util.Locale
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

fun formatTimeStamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}

fun generateRandomColor(): Color {
    return Color(
        red = Random.nextInt(128) + 128, // Range from 128 to 255
        green = Random.nextInt(128) + 128,
        blue = Random.nextInt(128) + 128
    )
}

@Composable
fun gradientBackgroundBrush(colors: List<Color>): Brush {
    val startOffset = Offset(Float.POSITIVE_INFINITY, 0f)
    val endOffset = Offset(0f, Float.POSITIVE_INFINITY)
    return Brush.linearGradient(
        colors = colors,
        start = startOffset,
        end = endOffset
    )
}