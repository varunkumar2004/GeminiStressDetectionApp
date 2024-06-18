package com.varunkumar.geminiapi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.varunkumar.geminiapi.presentation.viewModels.StatsState
import com.varunkumar.geminiapi.presentation.viewModels.StatsViewModel
import com.varunkumar.geminiapi.ui.theme.customButtonColors
import com.varunkumar.geminiapi.ui.theme.primary
import com.varunkumar.geminiapi.ui.theme.secondary
import com.varunkumar.geminiapi.ui.theme.secondaryTertiary
import com.varunkumar.geminiapi.ui.theme.tertiary

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

    Scaffold(
        containerColor = tertiary,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(text = "Sense", color = Color.Black)
                },
                actions = {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = secondary
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
                modifier = fModifier
                    .rotate(-90f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomSlider(
                    state = state,
                    height = TextFieldDefaults.MinHeight
                )
                CustomSlider(
                    state = state,
                    height = TextFieldDefaults.MinHeight
                )
                CustomSlider(
                    state = state,
                    height = TextFieldDefaults.MinHeight
                )
                CustomSlider(
                    state = state,
                    height = TextFieldDefaults.MinHeight
                )
            }

            Button(
                colors = customButtonColors(),
                modifier = fModifier
                    .height(TextFieldDefaults.MinHeight),
                onClick = { onSaveButtonClick() }
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
    state: StatsState,
    height: Dp
) {
    var sliderPosition by remember { mutableFloatStateOf(50f) }
    val radius = RoundedCornerShape(40.dp)

    Column(
        modifier = modifier
            .clip(radius)
            .background(secondaryTertiary)
            .padding(5.dp)
    ) {
        Slider(
            modifier = Modifier
                .width(1000.dp),
            steps = steps ?: 0,
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
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
                        containerColor = primary
                    )
                ) {

                }
            },
            track = {
                Column(
                    modifier = Modifier.width(1000.dp),
                ) {
                    Text(text = "Temperature", color = tertiary, textAlign = TextAlign.Justify)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyTemperatureSlider() {
}