package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.presentation.HealthSensors
import com.varunkumar.geminiapi.presentation.viewModels.StatsState
import com.varunkumar.geminiapi.presentation.viewModels.StatsViewModel
import com.varunkumar.geminiapi.ui.theme.primary
import com.varunkumar.geminiapi.ui.theme.primarySecondary
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.ui.theme.secondaryTertiary
import com.varunkumar.geminiapi.ui.theme.tertiary
import com.varunkumar.geminiapi.utils.Result
import com.varunkumar.geminiapi.utils.getScreenResolutionDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderScreen(
    modifier: Modifier = Modifier,
    viewModel: StatsViewModel,
    onSaveButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val fModifier = Modifier.fillMaxWidth()
    val (maxWidth, _) = getScreenResolutionDp()

    Scaffold(
        containerColor = secondary,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(text = "Sense", color = primary)
                },
                actions = {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = primarySecondary
                        ),
                        onClick = { onCancelButtonClick() }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = fModifier,
                text = "Tell us,\n" +
                        "How you have felt today?",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = primary
            )

            Column(
                modifier = fModifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomSlider(
                    modifier = fModifier,
                    state = state,
                    sensor = HealthSensors.HeartRateSensors,
                    viewModel = viewModel
                )
                CustomSlider(
                    modifier = fModifier,
                    state = state,
                    sensor = HealthSensors.SnoringRateSensors,
                    viewModel = viewModel
                )
                CustomSlider(
                    modifier = fModifier,
                    state = state,
                    sensor = HealthSensors.HoursOfSleepSensors,
                    viewModel = viewModel
                )
                CustomSlider(
                    modifier = fModifier,
                    state = state,
                    sensor = HealthSensors.RespirationRateSensors,
                    viewModel = viewModel
                )
            }


            state.responseResult?.let {
                Column(
                    modifier = fModifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (state.responseResult is Result.Loading) {
                            "Loading..."
                        } else {
                            state.responseResult.data ?: ""
                        }
                    )
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = tertiary
                ),
                modifier = fModifier
                    .height(TextFieldDefaults.MinHeight),
                onClick = viewModel::predictStress.also {
                    onSaveButtonClick()
                }
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    steps: Int? = null,
    radius: Dp = 40.dp,
    viewModel: StatsViewModel,
    height: Dp = TextFieldDefaults.MinHeight,
    state: StatsState,
    sensor: HealthSensors
) {
    val sliderPosition: Float = when (sensor) {
        is HealthSensors.SnoringRateSensors -> state.snoringRate
        is HealthSensors.RespirationRateSensors -> state.respirationRate
        is HealthSensors.BloodOxygenSensors -> state.bloodOxygen
        is HealthSensors.HeartRateSensors -> state.heartRate
        is HealthSensors.HoursOfSleepSensors -> state.sleep
        is HealthSensors.TemperatureSensors -> state.temperature
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(secondaryTertiary)
            .padding(2.dp),
    ) {
        Slider(
            modifier = modifier,
            //TODO do this width dynamically
            steps = steps ?: 0,
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
                        .size(height),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = primarySecondary
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = sliderPosition.toInt().toString(), color = Color.White)
                    }
                }
            },
            track = {
                Column(
                    modifier = Modifier.width(1000.dp),
                ) {
                    Text(
                        text = sensor.label,
                        color = secondary,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyTemperatureSlider() {
}