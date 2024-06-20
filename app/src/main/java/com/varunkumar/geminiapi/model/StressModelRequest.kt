package com.varunkumar.geminiapi.model

import com.google.gson.annotations.SerializedName

data class StressLevelRequest(
    @SerializedName("snoring_range") val snoringRange: Int,
    @SerializedName("respiration_rate") val respirationRate: Int,
    @SerializedName("temprature") val temperature: Int,
    @SerializedName("blood_oxygen") val bloodOxygen: Int,
    @SerializedName("sleep") val sleep: Int,
    @SerializedName("heart_rate") val heartRate: Int
)