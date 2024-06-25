package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.R

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    gradient: Brush,
    onScreenClick: () -> Unit
) {
    val fModifier = Modifier.fillMaxWidth()

    Box(
        modifier = modifier
            .background(brush = gradient)
            .clickable {
                onScreenClick()
            }
            .padding(16.dp)
    ) {
        Column(
            modifier = fModifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.welcome_illus),
                contentDescription = null
            )

            Text(
                text = "Welcome To\nStudent Well",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall
            )
        }

        Text(
            modifier = fModifier
                .align(Alignment.BottomCenter),
            textAlign = TextAlign.Center,
            text = "Tap to continue",
            style = MaterialTheme.typography.bodyMedium
        )
    }

}

fun Brush.Companion.customRadialGradient(colors: List<Color>): Brush {
    return radialGradient(
        colors = colors,
        center = Offset(0.5f, 0.5f),
        radius = 2000f
    )
}

@Preview(showBackground = true)
@Composable
private fun WelcomePrev() {
    WelcomeScreen(
        modifier = Modifier.fillMaxSize(),
        gradient = Brush.customRadialGradient(
            colors = listOf(
                Color(0xffFFCC9C),
                Color(0xffFFA16C)
            )
        ),
        onScreenClick = {}
    )
}
