package com.varunkumar.geminiapi.presentation.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.geminiapi.model.StressModelApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val stressModelApi: StressModelApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(
        StatsState()
    )
    val state = _state

    fun updateSensorValue(

    ) {
//        _state.update {
//        }

        viewModelScope.launch {
            try {
                val response = stressModelApi.getStressLevel(
                    snoringRange = 99,
                    respirationRate = 25,
                    temperature = 99,
                    bloodOxygen = 89,
                    sleep = 7,
                    heartRate = 100
                )
                if (response.isSuccessful) {
                    val stressLevel = response.body()?.stressLevel
                    Log.d("stress level", stressLevel.toString())
                } else {
                    Log.d("stress level", response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("error", e.toString())
            }
        }
    }
}

data class StatsState(
    val snoringRate: Int = 0,
    val respirationRate: Int = 0,
    val temperature: Int = 0,
    val bloodOxygen: Int = 0,
    val sleep: Int = 0,
    val heartRate: Int = 0
)

sealed class HealthSensor(val label: String, val low: Int, val high: Int) {
    data object SnoringRateSensor: HealthSensor("Snoring Rate", 0, 100)
    data object RespirationRateSensor: HealthSensor("Respiration Rate", 0, 100)
    data object TemperatureSensor: HealthSensor("Temperature", 0, 100)
    data object BloodOxygenSensor: HealthSensor("Blood Oxygen", 0, 100)
    data object HoursOfSleepSensor: HealthSensor("Sleep", 0, 100)
    data object HeartRateSensor: HealthSensor("Heart Rate", 0, 100)
}