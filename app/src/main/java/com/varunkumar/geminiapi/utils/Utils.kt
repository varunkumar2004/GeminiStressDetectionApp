package com.varunkumar.geminiapi.utils

import android.text.Spanned
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        red = Random.nextInt(128),
        green = Random.nextInt(128),
        blue = Random.nextInt(128)
    )
}

fun AnnotatedString.Builder.appendSuspend(spanned: Spanned) {
    val length = spanned.length
    var next: Int
    var index = 0

    while (index < length) {
        next = spanned.nextSpanTransition(index, length, Any::class.java)
        val spans = spanned.getSpans(index, next, Any::class.java)

        spans.forEach { span ->
            when (span) {
                is android.text.style.StyleSpan -> {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) {
                        append(spanned.subSequence(index, next))
                    }
                }
                else -> {
                    append(spanned.subSequence(index, next))
                }
            }
        }
        index = next
    }
}