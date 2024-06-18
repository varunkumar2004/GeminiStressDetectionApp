package com.varunkumar.geminiapi.presentation.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class StatsViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        StatsState()
    )
    val state = _state

    fun updateSensorValue() {

    }
}

data class StatsState(
    val heartRateSensor: HeartRateSensor = HeartRateSensor(),
    val bloodOxygenSensor: BloodOxygenSensor = BloodOxygenSensor(),
    val hoursOfSleepSensor: HoursOfSleepSensor = HoursOfSleepSensor(),
    val temperatureSensor: TemperatureSensor = TemperatureSensor()
)

sealed interface HealthSensor {
    val label: String
    val value: Float
    val low: Float
    val high: Float
}

class HeartRateSensor: HealthSensor {
    override val label: String = "Heart Rate"
    override val value: Float = 0f
    override val low: Float = 0f
    override val high: Float = 200f
}

class HoursOfSleepSensor: HealthSensor {
    override val label: String = "Blood Oxygen"
    override val value: Float = 0f
    override val low: Float = 0f
    override val high: Float = 100f
}

class BloodOxygenSensor: HealthSensor {
    override val label: String = "Blood Oxygen"
    override val value: Float = 0f
    override val low: Float = 0f
    override val high: Float = 100f
}

class TemperatureSensor: HealthSensor {
    override val label: String = "Blood Oxygen"
    override val value: Float = 0f
    override val low: Float = 0f
    override val high: Float = 100f
}