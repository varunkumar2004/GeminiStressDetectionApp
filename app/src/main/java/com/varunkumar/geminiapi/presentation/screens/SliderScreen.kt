package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.presentation.HealthSensors
import com.varunkumar.geminiapi.presentation.viewModels.SenseViewModel
import com.varunkumar.geminiapi.utils.getScreenResolutionDp

@Composable
fun SliderScreen(
    modifier: Modifier = Modifier,
    viewModel: SenseViewModel
) {
    val state = viewModel.state.collectAsState().value
    val fModifier = Modifier.fillMaxWidth()
    val (maxWidth, _) = getScreenResolutionDp()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(maxWidth),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CustomSlider(
                modifier = fModifier,
                sensor = HealthSensors.SnoringRateSensors,
                viewModel = viewModel,
                sliderPosition = state.snoringRate
            )
            CustomSlider(
                modifier = fModifier,
                sensor = HealthSensors.HoursOfSleepSensors,
                viewModel = viewModel,
                sliderPosition = state.sleep
            )
            CustomSlider(
                modifier = fModifier,
                sensor = HealthSensors.RespirationRateSensors,
                viewModel = viewModel,
                sliderPosition = state.respirationRate
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = fModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Extremely Negative",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Extremely Positive",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    viewModel: SenseViewModel,
    sliderPosition: Float,
    sensor: HealthSensors
) {
    val height = TextFieldDefaults.MinHeight
    val steps = 4
    val level = when (sliderPosition) {
        in 0.0..20.0 -> "Extremely Negative"
        in 20.1..40.0 -> "Negative"
        in 40.1..60.0 -> "Neutral"
        in 60.1..80.0 -> "Positive"
        else -> "Extremely Positive"
    }

    val shape = RoundedCornerShape(40.dp)

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .clip(shape)
                .border(0.5.dp, Color.Black, shape)
                .padding(5.dp),
        ) {
            Slider(
                modifier = modifier,
                //TODO do this width dynamically
                steps = steps,
                value = sliderPosition,
                onValueChange = { value ->
                    viewModel.onSliderChange(value, sensor)
                },
                valueRange = sensor.low..sensor.high,
                colors = SliderDefaults.colors(
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent
                ),
                thumb = {
                    ElevatedCard(
                        modifier = Modifier
                            .size(height - 5.dp),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black
                        ),
                        content = {}
                    )
                },
                track = {
                    Row(
                        modifier = modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            textAlign = TextAlign.Start,
                            text = sensor.label,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            textAlign = TextAlign.Start,
                            text = level,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyTemperatureSlider() {
}