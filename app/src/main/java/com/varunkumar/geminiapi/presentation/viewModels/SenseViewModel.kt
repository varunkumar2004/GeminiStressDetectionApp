package com.varunkumar.geminiapi.presentation.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.geminiapi.model.StressModelApi
import com.varunkumar.geminiapi.presentation.HealthSensors
import com.varunkumar.geminiapi.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SenseViewModel @Inject constructor(
    private val stressModelApi: StressModelApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(
        StatsState(
            imageUri = savedStateHandle.get<Uri>("imageUri")
        )
    )
    val state = _state.asStateFlow()

    fun onChangeImageUri(newUri: Uri?) {
        _state.update { it.copy(imageUri = newUri) }
        savedStateHandle["imageUri"] = newUri
    }

    fun onScreenChange() {
        _state.update {
            it.copy(isImageScreen = !it.isImageScreen)
        }
    }

    fun onSliderChange(newValue: Float, healthSensor: HealthSensors) {
        when (healthSensor) {
            is HealthSensors.SnoringRateSensors -> {
                _state.update {
                    it.copy(snoringRate = newValue)
                }
            }

            is HealthSensors.RespirationRateSensors -> {
                _state.update {
                    it.copy(respirationRate = newValue)
                }
            }

            else -> {}
//
//            is HealthSensors.BloodOxygenSensors -> {
//                _state.update {
//                    it.copy(bloodOxygen = newValue)
//                }
//            }
//
//            is HealthSensors.HeartRateSensors -> _state.update {
//                it.copy(heartRate = newValue)
//            }
//
//            is HealthSensors.HoursOfSleepSensors -> {
//                _state.update {
//                    it.copy(sleep = newValue)
//                }
//            }
//
//            is HealthSensors.TemperatureSensors -> _state.update {
//                it.copy(temperature = newValue)
//            }
        }
    }

    fun predictStress() {
        _state.update { it.copy(responseResult = Result.Loading()) }

        viewModelScope.launch {
            try {
                val response = stressModelApi.getStressLevel(
                    snoringRange = _state.value.snoringRate,
                    respirationRate = _state.value.respirationRate,
                    temperature = _state.value.temperature,
                    bloodOxygen = _state.value.bloodOxygen,
                    sleep = _state.value.sleep,
                    heartRate = _state.value.heartRate
                )

                _state.update {
                    it.copy(
                        responseResult = Result.Success(
                            response.body()?.stressLevel ?: "Not Defined"
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(responseResult = Result.Error(e.message)) }
                Log.d("error", e.toString())
            }
        }
    }
}

data class StatsState(
    // TODO implement type safe logic here for result
    val imageUri: Uri? = null,
    val isImageScreen: Boolean = true,
    val responseResult: Result<String>? = null,
    val snoringRate: Float = 50f,
    val respirationRate: Float = 50f,
    val temperature: Float = 50f,
    val bloodOxygen: Float = 50f,
    val sleep: Float = 50f,
    val heartRate: Float = 50f
)

