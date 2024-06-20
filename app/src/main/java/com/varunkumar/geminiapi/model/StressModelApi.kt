package com.varunkumar.geminiapi.model

import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StressModelApi {
    @Multipart
    @POST("predict")
    suspend fun getStressLevel(
        @Part("snoring_range") snoringRange: Int,
        @Part("respiration_rate") respirationRate: Int,
        @Part("temprature") temperature: Int,
        @Part("blood_oxygen") bloodOxygen: Int,
        @Part("sleep") sleep: Int,
        @Part("heart_rate") heartRate: Int
    ): Response<StressLevelResponse>
}