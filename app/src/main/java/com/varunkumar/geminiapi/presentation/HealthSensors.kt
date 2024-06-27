package com.varunkumar.geminiapi.presentation

import androidx.compose.ui.graphics.painter.Painter


sealed class HealthSensors(
    val label: String,
    val low: Float,
    val high: Float,
    val icon: Painter? = null,
    val unit: String? = null
) {
    data object SnoringRateSensors : HealthSensors("Snoring Rate", 0f, 100f, null)
    data object RespirationRateSensors : HealthSensors("Respiration Rate", 0f, 100f)
    data object HoursOfSleepSensors : HealthSensors("Sleep", 0f, 100f)
}