package com.varunkumar.geminiapi.model

import com.google.gson.annotations.SerializedName

data class StressLevelResponse(
    @SerializedName("Stress level :") val stressLevel: String
)