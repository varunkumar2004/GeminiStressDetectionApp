package com.varunkumar.geminiapi.model

import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StressModelApi {
    @Multipart
    @POST("predict")
    suspend fun getStressLevel(
        @Part("snoring_range") snoringRange: Float,
        @Part("respiration_rate") respirationRate: Float,
        @Part("temprature") temperature: Float = 100f,
        @Part("blood_oxygen") bloodOxygen: Float = 50f,
        @Part("sleep") sleep: Float,
        @Part("heart_rate") heartRate: Float = 85f
    ): Response<StressLevelResponse>
}